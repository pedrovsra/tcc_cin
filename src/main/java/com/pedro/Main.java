package com.pedro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
import com.pedro.entities.Track;
import com.pedro.entities.analysis_entities.AudioAnalysis;
import com.pedro.entities.analysis_entities.Segment;
import com.pedro.interfaces.IDictionary;
import com.pedro.storage.ChordsDictionaryHash;

// https://developers.google.com/api-client-library/java/google-oauth-java-client/

public class Main {

	private static String MUSIC_ID;

	private static final File DATA_STORE_DIR = new File(System.getProperty("user.home"), ".store/spotify_sample");

	private static DataStoreFactory DATA_STORE_FACTORY;

	private static final String scope = "user-read-private user-read-email";

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static final String TOKEN_SERVER_URL = "https://accounts.spotify.com/api/token";

	private static final String AUTH_SERVER_URL = "https://accounts.spotify.com/authorize";

	private static final String[] CHROMATIC_SCALE = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

	private static IDictionary dic;

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
		System.out.println("número de segmentos: " + size);
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

	private static void initDictionary() {
		// ACORDE C (DO)
		dic.addChord("CEG", "C");
		dic.addChord("CGE", "C");
		dic.addChord("EGC", "C");
		dic.addChord("ECG", "C");
		dic.addChord("GEC", "C");
		dic.addChord("GCE", "C");

		// ACORDE C# (DO SUSTENIDO)
		dic.addChord("C#FG#", "C#");
		dic.addChord("C#G#F", "C#");
		dic.addChord("FC#G#", "C#");
		dic.addChord("FG#C#", "C#");
		dic.addChord("G#FC#", "C#");
		dic.addChord("G#C3F", "C#");

		// ACORDE D (RE)
		dic.addChord("DF#A", "D");
		dic.addChord("DAF#", "D");
		dic.addChord("F#DA", "D");
		dic.addChord("F#AD", "D");
		dic.addChord("ADF#", "D");
		dic.addChord("AF#D", "D");

		// ACORDE D# (RE SUSTENIDO)
		dic.addChord("D#GA#", "D#");
		dic.addChord("D#A#G", "D#");
		dic.addChord("GD#A#", "D#");
		dic.addChord("GA#D#", "D#");
		dic.addChord("A#D#G", "D#");
		dic.addChord("A#GD#", "D#");

		// ACORDE E (MI)
		dic.addChord("EG#B", "E");
		dic.addChord("EBG#", "E");
		dic.addChord("G#EB", "E");
		dic.addChord("G#BE", "E");
		dic.addChord("BEG#", "E");
		dic.addChord("BG#E", "E");

		// ACORDE F (FA)
		dic.addChord("FAC", "F");
		dic.addChord("FCA", "F");
		dic.addChord("ACF", "F");
		dic.addChord("AFC", "F");
		dic.addChord("CAF", "F");
		dic.addChord("CFA", "F");

		// ACORDE F# (FA SUSTENIDO)
		dic.addChord("F#A#C#", "F#");
		dic.addChord("F#C#A#", "F#");
		dic.addChord("A#F#C#", "F#");
		dic.addChord("A#C#F#", "F#");
		dic.addChord("C#A#F#", "F#");
		dic.addChord("C#F#A#", "F#");

		// ACORDE G (SOL)
		dic.addChord("GBD", "G");
		dic.addChord("GDB", "G");
		dic.addChord("BGD", "G");
		dic.addChord("BDG", "G");
		dic.addChord("DGB", "G");
		dic.addChord("DBG", "G");

		// ACORDE G# (SOL SUSTENIDO)
		dic.addChord("G#CD#", "G#");
		dic.addChord("G#D#C", "G#");
		dic.addChord("D#G#C", "G#");
		dic.addChord("D#CG#", "G#");
		dic.addChord("CD#G#", "G#");
		dic.addChord("CG#D#", "G#");

		// ACORDE A (LA)
		dic.addChord("AC#E", "A");
		dic.addChord("AEC#", "A");
		dic.addChord("C#EA", "A");
		dic.addChord("C#AE", "A");
		dic.addChord("EAC#", "A");
		dic.addChord("EC#A", "A");

		// ACORDE A# (LA SUSTENIDO)
		dic.addChord("A#DF", "A#");
		dic.addChord("A#FD", "A#");
		dic.addChord("#DFA", "A#");
		dic.addChord("#DAF", "A#");
		dic.addChord("FA#D", "A#");
		dic.addChord("FDA#", "A#");

		// ACORDE B (SI)
		dic.addChord("BD#F#", "B");
		dic.addChord("BF#D#", "B");
		dic.addChord("F#BD#", "B");
		dic.addChord("F#D#B", "B");
		dic.addChord("D#BF#", "B");
		dic.addChord("D#F#B", "B");

		// ACORDE Am (LA MENOR)
		dic.addChord("ACE", "Am");
		dic.addChord("AEC", "Am");
		dic.addChord("CAE", "Am");
		dic.addChord("CEA", "Am");
		dic.addChord("EAC", "Am");
		dic.addChord("ECA", "Am");
	}

	private static void writeToFile(float[][] arr) throws IOException {
		FileWriter fw = new FileWriter("chroma.txt");
		String aux;
		for (int i = 0; i < 12; i++) {
			aux = "";
			System.out.println(arr[i].length);
			for (int j = 0; j < arr[i].length; j++) {
				aux += arr[i][j] + " ";
			}
			fw.write(aux + "\n");
		}
		fw.flush();
		fw.close();
	}

	private static void run(HttpRequestFactory requestFactory, String music_id) throws IOException {
		HttpRequest request;
		Gson gson = new GsonBuilder().create();
		// pegando informações da musica
		SpotifyUrl urlInfo = new SpotifyUrl("https://api.spotify.com/v1/tracks/" + music_id);
		request = requestFactory.buildGetRequest(urlInfo);
		Track t = gson.fromJson(request.execute().parseAsString(), Track.class);
		System.out.println(
				"Obtendo informações da música '" + t.getName() + "' de '" + t.getArtists().get(0).getName() + "'");

		SpotifyUrl urlAnalysis = new SpotifyUrl("https://api.spotify.com/v1/audio-analysis/" + music_id);
		request = requestFactory.buildGetRequest(urlAnalysis);

		dic = new ChordsDictionaryHash();

		String response = request.execute().parseAsString();

		AudioAnalysis au;
		int cont;

		try {
			cont = 0;
			au = gson.fromJson(response, AudioAnalysis.class);
			//printPitches(au);

			float[][] m = generateMatrix(au);
			writeToFile(m);
			// 64yrDBpcdwEdNY9loyEGbX - 21 guns
			// 1OtGo99uypkRbMqshBVFnn - cant go on without you

//			initDictionary();
//
//			List<Segment> seg = au.getSegments();
//			String a[], b, c;
//			for (int i = 0; i < seg.size(); i++) {
//				a = get3OrderedPitches(seg.get(i).getPitches());
//				b = a[0] + a[1] + a[2];
//				if (dic.getChordByNotes(b).getName() != null) {
//					c = "---- " + dic.getChordByNotes(b).getName();
//					cont++;
//				} else
//					c = "-";
//				System.out.println(b + " " + c);
//			}
//			System.out.println("número de acertos: " + cont);

		} catch (Exception e) {
			System.out.println("Não foi possível gerar a matriz de valores a partir dessa música :(");
		}

	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Digite o ID da música a ser analisada:");
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

			// float teste[] = new float[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
			// 11};
			// teste = get4OrderedPitches(teste);

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