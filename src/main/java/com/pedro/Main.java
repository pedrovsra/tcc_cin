package com.pedro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pedro.auth.OAuth2ClientCredentials;
import com.pedro.auth.SpotifyUrl;
import com.pedro.auxiliary.ChordTemplates;
import com.pedro.auxiliary.ExponentialMovingAverage;
import com.pedro.auxiliary.Viterbi;
import com.pedro.chroma.Pitch2CENS;
import com.pedro.chroma.iSignal2Chroma;
import com.pedro.entities.Chord;
import com.pedro.entities.Cifra;
import com.pedro.entities.Track;
import com.pedro.entities.analysis_entities.AudioAnalysis;
import com.pedro.entities.analysis_entities.Segment;
import com.pedro.interfaces.IDictionary;
import com.pedro.storage.ChordsDictionaryHash;

// https://developers.google.com/api-client-library/java/google-oauth-java-client/

public class Main {

	private static String MUSIC_ID;

	private static final String BASE_URL = "https://www.cifraclub.com.br/";

	private static final double EMA_ALPHA = 0.5;

	private static final double INITIAL_PROBABILITY = 0.5;

	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".store/spotify_sample");

	private static DataStoreFactory DATA_STORE_FACTORY;

	private static final String scope = "user-read-private user-read-email";

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static final String TOKEN_SERVER_URL = "https://accounts.spotify.com/api/token";

	private static final String AUTH_SERVER_URL = "https://accounts.spotify.com/authorize";

	private static final String[] CHROMATIC_SCALE = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

	private static IDictionary dic;

	// LISTA DE ACORDES DA MUSICA - POKER FACE
	private static String[] CHORD_SEQUENCE = { "Em", "G", "Em", "G", "Em", "G", "Em", "G", "Em", "C", "G", "D", "Em",
			"C", "G", "D", "Em", "G", "Em", "G", "Em", "G", "Em", "G", "Em", "G", "Em", "C", "G", "D", "Em", "C", "G",
			"D", "Em", "G", "Em", "G", "Em", "G", "Em", "C", "G", "D", "G", "D", "Em", "G", "Em", "G" };

	private static float[][] SEGMENTS;
	private static int QTDACORDES;
	private static int QTDSEGMENTOS;

	private static double MAX, MIN;

	private static double[][] PROB_MATRIX;
	private static double[][] EMISSION_MATRIX;
	private static double[] INITIAL_PROBALILITIES;

	private static Credential authorize() throws Exception {
		OAuth2ClientCredentials.errorIfNotSpecified();

		AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
				HTTP_TRANSPORT, JSON_FACTORY, new GenericUrl(TOKEN_SERVER_URL),
				new ClientParametersAuthentication(OAuth2ClientCredentials.API_KEY, OAuth2ClientCredentials.API_SECRET),
				OAuth2ClientCredentials.API_KEY, AUTH_SERVER_URL).setScopes(Arrays.asList(scope))
						.setDataStoreFactory(DATA_STORE_FACTORY).build();

		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(OAuth2ClientCredentials.DOMAIN)
				.setPort(OAuth2ClientCredentials.PORT).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	private static void printPitches(AudioAnalysis aa) {
		List<Segment> segments = aa.getSegments();
		int size = segments.size();
		System.out.println("nÃºmero de segmentos: " + size);
		for (int i = 0; i < 12; i++) {
			System.out.print(CHROMATIC_SCALE[i] + ": ");
			for (int j = 0; j < size; j++) {
				System.out.print(segments.get(j).getPitches()[i] + "\t");
			}
			System.out.println("");
		}
	}

	private static float[][] generateMatrix(AudioAnalysis aa) {
		List<Segment> segments = aa.getSegments();
		int size = segments.size();
		float[][] matrix = new float[12][size];

		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = segments.get(j).getPitches()[i];
			}
		}
		return matrix;
	}

	private static String[] get3OrderedPitches(float[] notes) {
		float aux[] = notes;
		String c[] = CHROMATIC_SCALE;

		boolean trocado;
		do {
			trocado = false;
			for (int i = 0; i < 11; i++) {
				if (aux[i] < aux[i + 1]) {
					// trocando os pitches
					float f = aux[i];
					aux[i] = aux[i + 1];
					aux[i + 1] = f;
					trocado = true;

					// trocando as notas
					String t = c[i];
					c[i] = c[i + 1];
					c[i + 1] = t;
				}
			}
		} while (trocado);

		// Arrays.sort(aux);
		return new String[] { c[0], c[1], c[2] };
	}

	private static void initProbMatrix(double base_prob, int num_chords) {
		PROB_MATRIX = new double[num_chords][num_chords];
		for (int i = 0; i < num_chords; i++) {
			for (int j = 0; j < num_chords; j++) {
				PROB_MATRIX[i][j] = base_prob;
			}
		}
	}

	private static void initialProbabilities(double base_prob, int num_chords) {
		INITIAL_PROBALILITIES = new double[num_chords];
		for (int i = 0; i < num_chords; i++) {
			INITIAL_PROBALILITIES[i] = base_prob;
		}
	}

	private static void initEmissionMatrix(double base_prob, int num_chords, int num_segments) {
		EMISSION_MATRIX = new double[num_segments][num_chords];
		for (int i = 0; i < num_segments; i++) {
			for (int j = 0; j < num_chords; j++) {
				EMISSION_MATRIX[i][j] = base_prob;
			}
		}
	}

	private static void writeToFile(float[][] arr) throws IOException {
		FileWriter fw = new FileWriter("chroma.txt");
		String aux;
		for (int i = 0; i < 12; i++) {
			aux = "";
			// System.out.println(arr[i].length);
			for (int j = 0; j < arr[i].length; j++) {
				aux += arr[i][j] + " ";
			}
			fw.write(aux + "\n");
		}
		fw.flush();
		fw.close();
	}

	private static void writeToFileC(double[][] arr) throws IOException {
		FileWriter fw = new FileWriter("chroma.txt");
		String aux;

		int s1 = arr.length, s2 = arr[0].length;
		System.out.println(s1 + "  " + s2);
		for (int i = 0; i < s2; i++) {
			aux = "";
			for (int j = 0; j < s1; j++) {
				aux += arr[j][i] + " ";
			}
			fw.write(aux + "\n");
		}
		fw.flush();
		fw.close();
	}

	private static float[][] calculateMovingAgerageDouble(double[][] arr) {
		float[][] aux = new float[arr.length][arr[0].length];
		ExponentialMovingAverage ema = new ExponentialMovingAverage(EMA_ALPHA);

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				aux[i][j] = (float) ema.average(arr[i][j]);
			}
		}

		return aux;
	}

	private static float[][] calculateMovingAgerage(float[][] arr) {
		float[][] aux = new float[12][arr[0].length];
		ExponentialMovingAverage ema = new ExponentialMovingAverage(EMA_ALPHA);

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				aux[i][j] = (float) ema.average(arr[i][j]);
			}
		}

		return aux;
	}

	private static float sumOfMult(float[] pcp, float[] template) {
		float sum = 0;
		for (int i = 0; i < 12; i++) {
			sum += (pcp[i] * template[i]);
		}
		return sum;
	}

	private static float median(float[] arr) {
		Arrays.sort(arr);
		int size = arr.length;
		if (size % 2 == 0) {
			int h = (size / 2) - 1;
			return ((arr[h] + arr[h + 1]) / 2);
		} else {
			return arr[size / 2];
		}
	}

	private static float[][] calculateStaticMovingAverage(float[][] arr, int L) {
		float[][] Ctraco = new float[12][arr[0].length];
		float aux2 = 0;

		int i, j, size = arr[0].length - 1;
		int s, aux = L % 2 == 0 ? (L / 2) - 1 : (L - 1) / 2;
		for (int b = 0; b < 12; b++) {
			for (int m = aux; m < size; m++) {
				for (int c = m, l = 0; c < L && c < size - aux; c++, l++)
					aux2 += arr[b][c + l - aux];

				Ctraco[b][m - aux] = aux2 / L;
			}
		}

		return Ctraco;
	}

	private static float[][] calculateStaticMovingMedian(float[][] arr, int L) {
		float[][] Ctraco = new float[12][arr[0].length];
		float[] aux2;

		int i, j, size = arr[0].length - 1;
		int s, aux = L % 2 == 0 ? (L / 2) - 1 : (L - 1) / 2;
		for (int b = 0; b < 12; b++) {
			for (int m = aux; m < size; m++) {
				int q = m - Math.floorDiv((L - 1), 2), p = m + (int) Math.ceil((L - 1) / 2);
				aux2 = new float[L];

				for (int c = q, k = 0; c <= p && p < size - aux; c++, k++)
					aux2[k] = arr[b][c];

				Ctraco[b][m - aux] = median(aux2);
			}
		}

		return Ctraco;
	}

	private static String tratarNomeAcorde(String acorde) {

		if (acorde.contains("#")) {
			acorde = acorde.replace('#', 's');
		}

		return acorde;
	}

	private static Map<String, float[]> getChordTemplates(Map<String, Integer> acordes) {
		Map<String, float[]> templates = new HashMap<String, float[]>();
		Chord c;

		for (Map.Entry<String, Integer> entry : acordes.entrySet()) {
			c = new Chord(entry.getKey(), ChordTemplates.valueOf(tratarNomeAcorde(entry.getKey())).getTemplate());
			templates.put(c.getName(), c.getTemplate());
		}

		return templates;
	}

	private static float[][] calculateDynamicMovingMedian(float[][] arr, int L) {
		float[][] Ctraco = new float[12][arr[0].length];
		float[] aux2;

		int i, j, size = arr[0].length - 1;
		int s;
		for (int b = 0; b < 12; b++) {
			for (int m = 0; m < size; m++) {
				int q = m - Math.floorDiv((L - 1), 2), p = m + (int) Math.ceil((L - 1) / 2);
				i = q > 0 ? q : 0;
				j = p <= size ? p : size - 1;
				s = j - i < L - 1 ? j - i : L;
				aux2 = new float[s];
				if ((j - i <= 12) && !(j == 12 && i == 0))
					j--;

				for (int c = i, k = 0; c <= j; c++, k++)
					aux2[k] = arr[b][c];

				Ctraco[b][m] = median(aux2);
			}
		}

		return Ctraco;
	}

	private static String prepareUrl(String song, String artist) {
		song = song.replace("É", "");
		song = song.replace("é", "");

		artist = artist.replace("É", "");
		artist = artist.replace("é", "");

		String normalizedSong = Normalizer.normalize(song, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		String normalizedArtist = Normalizer.normalize(artist, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

		String[] songArr = normalizedSong.toLowerCase().split(" ");
		String[] artistArr = normalizedArtist.toLowerCase().split(" ");

		String url = BASE_URL;

		url += artistArr[0];
		for (int i = 1; i < artistArr.length; i++) {
			url += "-" + artistArr[i];
		}
		url += "/";

		url += songArr[0];
		for (int i = 1; i < songArr.length; i++) {
			url += "-" + songArr[i];
		}

		return url;
	}

	private static double[][] normalize(double[][] mat) {
		double[][] norm = new double[mat.length][mat[0].length];

		double maxMenosMin = MAX - MIN;

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				norm[i][j] = (mat[i][j] - MIN) / maxMenosMin;
			}
		}

		return norm;
	}

	private static double arrSum(double[] arr) {
		double sum = 0;

		for (int i = 0; i < arr.length; i++)
			sum += arr[i];

		return sum;
	}

	private static double[][] toPercent(double[][] mat) {
		double[][] per = new double[mat.length][mat[0].length];
		double[] aux = new double[mat.length];
		double s;

		int s1 = mat.length, s2 = mat[0].length;
		System.out.println(s1 + "  " + s2);
		for (int i = 0; i < s2; i++) {
			for (int j = 0; j < s1; j++) {
				aux[j] = mat[j][i];
			}
			s = arrSum(aux);
			for (int j = 0; j < s1; j++) {
				per[j][i] = mat[j][i] / s;
			}
		}
		return per;
	}

	private static float[] getPCP(int index) {
		float[] aux = new float[12];
		for (int i = 0; i < 12; i++) {
			aux[i] = SEGMENTS[i][index];
		}
		return aux;
	}

	private static void run(HttpRequestFactory requestFactory, String music_id) throws IOException {
		HttpRequest request;
		Gson gson = new GsonBuilder().create();
		// pegando informaÃ§Ãµes da musica
		SpotifyUrl urlInfo = new SpotifyUrl("https://api.spotify.com/v1/tracks/" + music_id);
		request = requestFactory.buildGetRequest(urlInfo);
		Track t = gson.fromJson(request.execute().parseAsString(), Track.class);
		System.out.println(
				"Obtendo informações da música '" + t.getName() + "' de '" + t.getArtists().get(0).getName() + "'");

		// for (int v = 0; v < teste.size(); v++) {
		// System.out.println(teste.entrySet().toArray()[v]);
		// }
		// for (Map.Entry<String, float[]> entry : teste.entrySet()) {
		// System.out.println(entry.getKey());
		// }
		// Element pre = doc.select("cifra-tom").first();
		// Element tom = doc.getElementById("cifra_tom");
		// System.out.println(tom.html());

		// System.out.println(doc.html());
		// System.exit(0);

		SpotifyUrl urlAnalysis = new SpotifyUrl("https://api.spotify.com/v1/audio-analysis/" + music_id);
		request = requestFactory.buildGetRequest(urlAnalysis);

		dic = new ChordsDictionaryHash();

		String response = request.execute().parseAsString();

		AudioAnalysis au;
		int cont;

		try {

			String url = prepareUrl(t.getName(), t.getArtists().get(0).getName());

			System.out.println(url);
			Cifra c = new Cifra(Jsoup.connect(url).get());
			int numAcordes = c.getAcordes().size();

			Map<String, float[]> teste = getChordTemplates(c.getMapChords());

			cont = 0;
			au = gson.fromJson(response, AudioAnalysis.class);
			// printPitches(au);

			float[][] m = generateMatrix(au); // CHROMAGRAM SEM TRATAMENTO
			SEGMENTS = m;
			float[][] m2 = calculateDynamicMovingMedian(m, 7);
			// float[][] m2 = calculateStaticMovingMedian(m, 7);

			// iSignal2Chroma p2c = new Pitch2CENS();
			// ArrayList<double[]> arrl = new ArrayList<double[]>();
			// double[] aux;
			// for (int i = 0; i < 12; i++) {
			// aux = new double[m[i].length];
			// System.out.println(m[i].length);
			// for (int j = 0; j < m[i].length; j++) {
			// aux[j] = m[i][j];
			// }
			// arrl.add(aux);
			// }

			QTDACORDES = teste.size();
			QTDSEGMENTOS = m[0].length;

			List<String> states = new ArrayList<String>();

			// MATRIZ DE PROBABILIDADES DE EMISSAO DE TAMANHO (qtd de acordes x
			// segmentos)
			double[][] emissionProbMatrix = new double[QTDACORDES][QTDSEGMENTOS];
			int v = 0;
			MAX = Double.MIN_VALUE;
			MIN = Double.MAX_VALUE;
			for (Map.Entry<String, float[]> entry : teste.entrySet()) {
				states.add(entry.getKey());
				for (int x = 0; x < QTDSEGMENTOS; x++) {
					emissionProbMatrix[v][x] = sumOfMult(getPCP(x), entry.getValue());

					MAX = Math.max(emissionProbMatrix[v][x], MAX);
					MIN = Math.min(emissionProbMatrix[v][x], MIN);
				}
				v++;
			}

			// System.out.println(MAX);
			// System.out.println(MIN);

			// for (int x = 0; x < QTDACORDES; x++) {
			// for (int l = 0; l < QTDSEGMENTOS; l++) {
			// System.out.print(probMatrix[x][l] + " ");
			// }
			// System.out.println("");
			// }

			// NORMALIZANDO MATRIZ DE PROBABILIDADES DE EMISSAO (0 a 1)
			emissionProbMatrix = normalize(emissionProbMatrix);

			// System.out.println("");

			// for (int x = 0; x < QTDACORDES; x++) {
			// for (int l = 0; l < QTDSEGMENTOS; l++) {
			// System.out.print(emissionProbMatrix[x][l] + " ");
			// }
			// System.out.println("");
			// }

			// PASSANDO AS PROBABILIDADES PARA PORCENTAGEM
			emissionProbMatrix = toPercent(emissionProbMatrix);
			// writeToFileC(emissionProbMatrix);

			// calculando CENS do pitch
			// double[][] q = p2c.signal2Chroma(arrl, false); // CHROMAGRAN COM
			// CENS
			// writeToFile(m);
			// writeToFileC(q);

			// calculando a moving average (pre-filtro)
			// float[][] ma = calculateMovingAgerageDouble(q); // CHROMAGRAM COM
			// MOVING AVERAGE
			// writeToFile(m2);

			// O CHROMAGRAM � O MEU CONJUNTO DE OBSERVACOES

			// 64yrDBpcdwEdNY9loyEGbX - 21 guns
			// 1QV6tiMFM6fSOKOGLMHYYg - poker face USAR PRA TESTE
			// 1OtGo99uypkRbMqshBVFnn - cant go on without you
			// 66OsFOW2GHEnmGGbMpvB66 - the stage
			// 5V1AHQugSTASVez5ffJtFo - let it be
			// python plot_matrix.py --chroma chroma.txt

			// initDictionary();

			// initProbMatrix(0.5, 4); // ESTADOS

			// STATES: acordes presentes na musica
			// OBSERVACOES: o pcp de cada segmento
			// PROBABILIDADE A PRIORI: 1 pro primeiro acorde da musica, 0 pro
			// resto
			// PROBALIDIDADE DE EMISSAO: soma das multiplicaes do pcp com
			// template
			// PROBABILIDADE DE TRANSICAO: 1/x para mudar e (x-1)/x para ficar,
			// onde x = (seg/acorde)

			Viterbi vi = new Viterbi();

			List<String> acordes = c.getAcordes();

			float segPorAcorde = QTDSEGMENTOS / numAcordes;

			double probFicar = (segPorAcorde - 1) / segPorAcorde;
			double probMudar = 1.0 / (double) QTDACORDES;

			// definindo as probabilidades de transicao -----------------
			double[][] trans_prob = new double[QTDACORDES][QTDSEGMENTOS];
			for (int i = 0; i < QTDACORDES; i++) {
				for (int j = 0; j < QTDSEGMENTOS; j++) {
					trans_prob[i][j] = probMudar;
				}
			}
			// ----------------------------------------------------------

			// definindo as probabilidades iniciais -----------
			double[] start_prob = new double[states.size()];
			start_prob[0] = 1;
			for (int s = 1; s < states.size(); s++) {
				start_prob[s] = 0;
			}
			// -------------------------------------------------

			// definindo as observacoes -----------------------
			// String[] observations = new String[acordes.size()];
			// for (int s = 0; s < acordes.size(); s++) {
			// observations[s] = acordes.get(s);
			// }
			// -------------------------------------------------

			// definindo os estados -----------------------
			String[] estados = new String[states.size()];
			for (int s = 0; s < states.size(); s++) {
				estados[s] = states.get(s);
			}
			// --------------------------------------------
			// vi.setStates(estados);
			vi.forwardViterbi(50, estados, start_prob, trans_prob, emissionProbMatrix);
			// TODO

			// List<Segment> seg = au.getSegments();
			// String a[], b, c;
			// for (int i = 0; i < seg.size(); i++) {
			// a = get3OrderedPitches(seg.get(i).getPitches());
			// b = a[0] + a[1] + a[2];
			// if (dic.getChordByNotes(b).getName() != null) {
			// c = "---- " + dic.getChordByNotes(b).getName();
			// cont++;
			// } else
			// c = "-";
			// System.out.println(b + " " + c);
			// }
			// System.out.println("nÃºmero de acertos: " + cont);

		} catch (Exception e) {
			System.out.println("Não foi possível gerar a matriz de valores a partir dessa música :(");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Digite o ID da musica a ser analisada:");
		MUSIC_ID = in.nextLine();
		in.close();
		try {
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
			final Credential credential = authorize();
			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
					credential.initialize(request);
					request.setParser(new JsonObjectParser(JSON_FACTORY));
				}
			});

			// DO IT
			run(requestFactory, MUSIC_ID);

			// Success!
			return;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(1);
	}
}