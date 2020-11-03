package br.com.zup.estrelas.dayana.jogoForca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {

	public static void main(String[] args) {

		JogoForca jf = new JogoForca();

		jf.iniciaJogo();
	}

	static class JogoForca {

		private final int TOTAL_DE_ERROS = 6;
		private ArrayList<String> palavras = new ArrayList<String>();
		private String[] palavraEscolhida;
		private String[] letrasDigitadas;
		private int tentativas = 6;

		public void iniciaJogo() {

			try {
				lerArquivo();
			} catch (IOException e) {
				System.out.println("O arquivo não pôde ser lido!");
				return;
			}

			palavraEscolhida = buscaPalavraAleatoria();
			letrasDigitadas = new String[palavraEscolhida.length + TOTAL_DE_ERROS];

			inicializarParteGrafica();

			letrasDigitadas = new String[palavraEscolhida.length];

			while (tentativas > 0) {

				String letraDigitada = recuperaLetraDigitada();
				if (letraDigitada == null) {
					return;
				}

				if (!verificaLetraDigitada(letraDigitada)) {
					System.out.println("Letra não encontrada.Tente novamente!");
					tentativas--;
				}

			}
			System.out.println("Não foi dessa vez. Tente novamente!");
		}

		private void lerArquivo() throws IOException {

			BufferedReader leituraDeArquivo = new BufferedReader(new FileReader("palavras.txt"));
			String linhaLida;
			while ((linhaLida = leituraDeArquivo.readLine()) != null) {
				palavras.add(linhaLida);
			}
			leituraDeArquivo.close();
		}

		private String[] buscaPalavraAleatoria() {

			Random numeroRandomico = new Random();

			int tamanhoArrayPalavras = palavras.size();
			int posicaoAleatoria = numeroRandomico.nextInt(tamanhoArrayPalavras);
			String palavraSelecionada = palavras.get(posicaoAleatoria);
			String palavraSemAcento = retiraAcentuacao(palavraSelecionada);
			String[] palavraArray = new String[palavraSemAcento.length()];

			char[] palavraSemAcentoArray = palavraSemAcento.toCharArray();
			for (int i = 0; i < palavraSemAcento.length(); i++) {
				palavraArray[i] = String.valueOf(palavraSemAcentoArray[i]);
			}

			return palavraArray;
		}

		private String retiraAcentuacao(String palavra) {
			return Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		private void inicializarParteGrafica() {

			System.out.println("Bem vindo ao jogo da Forca.\nDigite uma letra para iniciar...\n");

			for (int i = 0; i < palavraEscolhida.length; i++) {

				System.out.print("_ ");

			}
		}

		private String recuperaLetraDigitada() {
			Scanner teclado = new Scanner(System.in);

			String letraDigitada = teclado.next();

			teclado.close();

			if (letraDigitada == null || letraDigitada.trim().length() == 0) {
				System.out.println("Necessário digitar uma letra");
				return null;
			}

			char[] letraDigitadaArray = letraDigitada.toCharArray();
			String letraDigitadaString = String.valueOf(letraDigitadaArray[0]);
			return letraDigitadaString;

		}

		private boolean verificaLetraDigitada(String letraDigitada) {
			for (int i = 0; i < palavraEscolhida.length; i++) {
				if (letraDigitada.equals(palavraEscolhida[i])) {
					return true;
				}
			}
			return false;

		}

	}
}
