package com.pedro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;

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
import com.pedro.auxiliary.MeuViterbi;
import com.pedro.entities.Chord;
import com.pedro.entities.Cifra;
import com.pedro.entities.PitchClassProfile;
import com.pedro.entities.Track;
import com.pedro.entities.analysis_entities.AudioAnalysis;
import com.pedro.entities.analysis_entities.Segment;

// https://developers.google.com/api-client-library/java/google-oauth-java-client/

class AcordeTempo {
	private String nome;
	private float inicio;
	private float duracao;

	public AcordeTempo(String nome, float inicio, float duracao) {
		super();
		this.nome = nome;
		this.inicio = inicio;
		this.duracao = duracao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getInicio() {
		return inicio;
	}

	public void setInicio(float inicio) {
		this.inicio = inicio;
	}

	public float getDuracao() {
		return duracao;
	}

	public void setDuracao(float duracao) {
		this.duracao = duracao;
	}
}

public class Main {

	private static String MUSIC_ID;

	private static final String BASE_URL = "https://www.cifraclub.com.br/";

	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".store/spotify_sample");

	private static DataStoreFactory DATA_STORE_FACTORY;

	private static final String scope = "user-read-private user-read-email";

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static final String TOKEN_SERVER_URL = "https://accounts.spotify.com/api/token";

	private static final String AUTH_SERVER_URL = "https://accounts.spotify.com/authorize";

	// LISTAS DE ACORDES
	// -----------------------------------------------------------------------------------------------
	private static String[] POKER_FACE = { "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#", "G#m",
			"E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#", "G#m",
			"E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#", "B", "F#", "G#m", "B", "G#m",
			"B" };
	private static String[] ESTADOS_POKER_FACE = { "Em", "G", "C", "D" };

	private static String[] ISLAND_IN_THE_SUN = { "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#",
			"G#m", "E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#",
			"G#m", "E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#", "B", "F#", "G#m", "B",
			"G#m", "B" };
	private static String[] ESTADOS_ISLAND_IN_THE_SUN = { "Em", "G", "C", "D" };

	private static String[] THE_SUBURBS = { "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#",
			"G#m", "E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#",
			"G#m", "E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#", "B", "F#", "G#m", "B",
			"G#m", "B" };
	private static String[] ESTADOS_THE_SUBURBS = { "Em", "G", "C", "D" };

	private static String[] THE_SCIENTIST = { "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#",
			"G#m", "E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#",
			"G#m", "E", "B", "F#", "G#m", "B", "G#m", "B", "G#m", "B", "G#m", "E", "B", "F#", "B", "F#", "G#m", "B",
			"G#m", "B" };
	private static String[] ESTADOS_THE_SCIENTIST = { "Em", "G", "C", "D" };

	// ----------------------------------------------------------------------------------------------------------------

	private static double TRANS_PROB = 0.5;

	private static float[][] SEGMENTS;
	private static int QTDACORDES;
	private static int QTDSEGMENTOS;
	private static int ACORDES;

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

	private static void writeToFile(float[][] arr) throws IOException {
		FileWriter fw = new FileWriter("chroma.txt");
		String aux;
		for (int i = 0; i < 12; i++) {
			aux = "";
			// System.out.println(arr[i].length);
			for (int j = 0; j < arr[i].length; j++) {
				aux += arr[i][j] + "\t";
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

	private static List<Chord> getChordTemplates(List<String> acordes) {
		List<Chord> templates = new ArrayList<Chord>();
		Chord c;

		for (String acorde : acordes) {
			c = new Chord(acorde, ChordTemplates.valueOf(tratarNomeAcorde(acorde)).getTemplate());
			templates.add(c);
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
		song = song.replace("'", "");
		song = song.replace("/", " ");

		artist = artist.replace("/", " ");
		artist = artist.replace("'", "");
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
		// System.out.println(s1 + " " + s2);
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

	private static PitchClassProfile[] toPCPArray(float[][] mat) {
		PitchClassProfile[] pcps = new PitchClassProfile[mat[0].length];
		double[] aux = new double[12];
		int s1 = mat.length, s2 = mat[0].length;
		for (int i = 0; i < s2; i++) {
			for (int j = 0; j < s1; j++) {
				aux[j] = mat[j][i];
			}
			pcps[i] = new PitchClassProfile(aux);
		}

		return pcps;
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

		SpotifyUrl urlAnalysis = new SpotifyUrl("https://api.spotify.com/v1/audio-analysis/" + music_id);
		request = requestFactory.buildGetRequest(urlAnalysis);

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

			// for (int i = 0; i < 12; i++) {
			// // System.out.println(arr[i].length);
			// for (int j = 0; j < m[i].length; j++) {
			// System.out.printf("%.3f ", m[i][j]);
			// }
			// System.out.println("");
			// }

			writeToFile(m);
			// float[][] m2 = calculateDynamicMovingMedian(m, 7);

			QTDACORDES = teste.size();
			QTDSEGMENTOS = m[0].length;

			List<String> acordes = c.getAcordes();
			ACORDES = acordes.size();
			List<Chord> teste2 = getChordTemplates(acordes);

			List<String> states = new ArrayList<String>();

			// MATRIZ DE PROBABILIDADES DE EMISSAO DE TAMANHO (qtd de acordes x
			// segmentos)
			double[][] emissionProbMatrix = new double[ACORDES][QTDSEGMENTOS];
			int v = 0;
			MAX = Double.MIN_VALUE;
			MIN = Double.MAX_VALUE;

			for (Chord ch : teste2) {
				states.add(ch.getName());
				for (int x = 0; x < QTDSEGMENTOS; x++) {
					emissionProbMatrix[v][x] = sumOfMult(getPCP(x), ch.getTemplate());

					MAX = Math.max(emissionProbMatrix[v][x], MAX);
					MIN = Math.min(emissionProbMatrix[v][x], MIN);
				}
				v++;
			}

			// NORMALIZANDO MATRIZ DE PROBABILIDADES DE EMISSAO (0 a 1)
			emissionProbMatrix = normalize(emissionProbMatrix);

			// GERANDO UM ARRAY DE PCP
			PitchClassProfile[] pcps = toPCPArray(SEGMENTS);

			// PASSANDO AS PROBABILIDADES PARA PORCENTAGEM
			emissionProbMatrix = toPercent(emissionProbMatrix);

			// O CHROMAGRAM � O MEU CONJUNTO DE OBSERVACOES

			// 1QV6tiMFM6fSOKOGLMHYYg - POKER FACE
			// 4OaV9UYQ3EfrBRPjoO6u7c - HEART SHAPED BOX
			// 26D1PRJjD9Jj1JGRk88KVc - LITHIUM
			// 33iQW2OneB0oNh2NfrAzqW - BLITZKRIEG BOP
			// 07b5vArZtW08PuEqCw61Ei - PET SEMATARY
			// 3k1WwLG1OXCm6iQ13VrJEL - THE JACK
			// 77NNZQSqzLNqh2A9JhLRkg - DONT STOP BELIEVIN
			// 11Ojp7JniVvwd0gmgvyKkd - WRONG SIDE OF HEAVEN

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
			
			/*
			 Lady Gaga - Poker Face -> https://www.cifraclub.com.br/lady-gaga/poker-face 
			 Nirvana - Lithium -> https://www.cifraclub.com.br/nirvana/lithium/
			 Nirvana - Heart Shaped Box -> https://www.cifraclub.com.br/nirvana/heart-shaped-box/
			 Ramones - Blitzkrieg Bop -> https://www.cifraclub.com.br/ramones/blitzkrieg-bop-7419/
			 Ramones - Pet Semetary -> https://www.cifraclub.com.br/ramones/pet-sematary/
			 AC/DC - The Jack -> https://www.cifraclub.com.br/ac-dc/the-jack/
			 Journey - Don't Stop Believin' -> https://www.cifraclub.com.br/journey/dont-stop-believin/
			 Five Finger Death Punch - Wrong Side Of Heaven -> https://www.cifraclub.com.br/five-finger-death-punch/wrong-side-of-heaven/
			 */

			MeuViterbi vi = new MeuViterbi();

			System.out.println("Acordes da cifra");
			System.out.println(acordes.toString());
			// System.exit(0);

			float segPorAcorde = QTDSEGMENTOS / numAcordes;
			System.out.println(QTDSEGMENTOS);

			double probFicar = (segPorAcorde - 1) / segPorAcorde;
			double probMudar = 1.0 / (double) QTDACORDES;

			// definindo as probabilidades de transicao -----------------
			// double[][] trans_prob = new double[QTDACORDES][QTDACORDES];
			// for (int i = 0; i < QTDACORDES; i++) {
			// for (int j = 0; j < QTDACORDES; j++) {
			// trans_prob[i][j] = probMudar;
			// }
			// }

			double[][] trans_prob = new double[ACORDES][ACORDES];
			for (int i = 0; i < ACORDES - 1; i++) {
				// for (int j = 0; j < ACORDES - 1; j++) {
				// if (i == j) {
				trans_prob[i][i] = TRANS_PROB;
				trans_prob[i][i + 1] = TRANS_PROB;
				// }
				// }
			}
			trans_prob[ACORDES - 1][ACORDES - 1] = TRANS_PROB;

			// for (int i = 0; i < ACORDES; i++) {
			// for (int j = 0; j < ACORDES; j++) {
			// System.out.print(trans_prob[i][j] + " ");
			// }
			// System.out.println("");
			// }
			// ----------------------------------------------------------

			// definindo as probabilidades iniciais -----------
			double[] start_prob = new double[states.size()];
			start_prob[0] = 1;
			for (int s = 1; s < states.size(); s++) {
				start_prob[s] = 0.1;
				start_prob[0] -= 0.1;
			}
			// -------------------------------------------------

			int estado_final = -1;

			for (int i = 0; i < states.size(); i++) {
				if (states.get(i).equals(acordes.get(acordes.size() - 1))) {
					estado_final = i;
				}
			}

			// definindo os estados -----------------------
			String[] estados = new String[acordes.size()];
			for (int s = 0; s < acordes.size(); s++) {
				estados[s] = acordes.get(s);
			}
			// --------------------------------------------

			String[] resultados = vi.run(pcps, estados, trans_prob, emissionProbMatrix, start_prob);
			List<Segment> segs = au.getSegments();
			String st = resultados[0];
			float ini = segs.get(0).getStart();
			List<AcordeTempo> acs = new ArrayList<AcordeTempo>();
			float dur = segs.get(0).getDuration();
			for (int i = 1; i < resultados.length; i++) {
				if (!resultados[i].equals(st)) {
					acs.add(new AcordeTempo(st, ini, dur));

					st = resultados[i];
					ini = segs.get(i).getStart();
					dur = segs.get(i).getDuration();
				} else {
					dur += segs.get(i).getDuration();
				}
			}
			AcordeTempo aux;
			for (int i = 0; i < acs.size(); i++) {
				aux = acs.get(i);
				System.out.println(aux.getNome() + ":" + aux.getInicio() + ":" + aux.getDuracao());
			}

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