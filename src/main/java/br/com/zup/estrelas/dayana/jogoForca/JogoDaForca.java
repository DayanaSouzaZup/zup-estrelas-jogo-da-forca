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
		private String[] palavraHud;
		private int tentativas = 6;

		public void iniciaJogo() {

			try {
				lerArquivo();
			} catch (IOException e) {
				System.out.println("O arquivo não pôde ser lido!");
				return;
			}

			palavraEscolhida = buscaPalavraAleatoria();
			palavraHud = new String[palavraEscolhida.length];
			letrasDigitadas = new String[palavraEscolhida.length + TOTAL_DE_ERROS];

			inicializarParteGrafica();

			Scanner teclado = new Scanner(System.in);
			int posicaoAtualLetrasEncontradas = 0;

			try {

				while (tentativas > -1) {

					String letraDigitada = recuperaLetraDigitada(teclado);
					if (letraDigitada == null) {
						return;
					}

					if (verificaLetraRepetida(letraDigitada)) {
						System.out.println("Você já digitou a letra: " + letraDigitada + " .Tente novamente");
						continue;

					} else {
						letrasDigitadas[posicaoAtualLetrasEncontradas] = letraDigitada;
						posicaoAtualLetrasEncontradas++;

					}

					if (!verificaLetraDigitada(letraDigitada)) {
						System.out.println("Letra não encontrada.Tente novamente!");
						tentativas--;
					}
					preencheHud(letraDigitada);

					if (tentativas < 0) {
						System.out.println("\nNão foi dessa vez. Tente novamente!");
						break;
					}
					if (verificaVitoria()) {
						System.out.println("\nVocê ganhou!");
						break;
					}

				}
			} finally {
				teclado.close();
			}

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

			System.out.println("Bem vindo ao jogo da Forca.");

			for (int i = 0; i < palavraHud.length; i++) {

				palavraHud[i] = "_";

			}
			imprimeArray(palavraHud);
		}

		private String recuperaLetraDigitada(Scanner teclado) {

			System.out.println("\nDigite uma letra: ");
			String letraDigitada = teclado.next().toUpperCase();

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

		private boolean verificaLetraRepetida(String letraDigitada) {
			for (int i = 0; i < letraDigitada.length(); i++) {
				if (letraDigitada.equals(letrasDigitadas[i])) {
					return true;
				}

			}
			return false;
		}

		private void imprimeArray(String[] array) {
			for (int i = 0; i < array.length; i++) {
				System.out.print(array[i] + " ");
			}
		}

		private void preencheHud(String letraDigitada) {
			for (int i = 0; i < palavraEscolhida.length; i++) {
				if (letraDigitada.equals(palavraEscolhida[i])) {
					palavraHud[i] = letraDigitada;
				}
			}
			imprimeArray(palavraHud);
		}

		private boolean verificaVitoria() {
			for (int i = 0; i < palavraEscolhida.length; i++) {

				if (!palavraEscolhida[i].equals(palavraHud[i])) {
					return false;
				}
			}
			return true;
		}

	}
}
