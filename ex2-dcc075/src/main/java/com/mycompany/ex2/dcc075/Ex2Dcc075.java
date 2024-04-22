package com.mycompany.ex2.dcc075;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Samuel
 */
public class Ex2Dcc075 {


    public static String encriptar(int chave, String texto) {
        StringBuilder textoCifrado = new StringBuilder();
        int tamanhoTexto = texto.length();

        for (int c = 0; c < tamanhoTexto; c++) {
            char caractereOriginal = texto.charAt(c);
            if (Character.isLetter(caractereOriginal)) {
                int deslocamento = Character.isUpperCase(caractereOriginal) ? 'A' : 'a';
                int letraCifradaASCII = ((int) caractereOriginal - deslocamento + chave) % 26;
                if (letraCifradaASCII < 0) {
                    letraCifradaASCII += 26;
                }
                letraCifradaASCII += deslocamento;
                textoCifrado.append((char) letraCifradaASCII);
            } else {
                textoCifrado.append(caractereOriginal); 
            }
        }

        return textoCifrado.toString();
    }

    public static String decriptar(int chave, String textoCifrado) {
        StringBuilder texto = new StringBuilder();
        int tamanhoTexto = textoCifrado.length();

        for (int c = 0; c < tamanhoTexto; c++) {
            char caractereCifrado = textoCifrado.charAt(c);
            if (Character.isLetter(caractereCifrado)) {
                int deslocamento = Character.isUpperCase(caractereCifrado) ? 'A' : 'a';
                int letraDecifradaASCII = ((int) caractereCifrado - deslocamento - chave + 26) % 26;
                if (letraDecifradaASCII < 0) {
                    letraDecifradaASCII += 26; 
                }
                letraDecifradaASCII += deslocamento;
                texto.append((char) letraDecifradaASCII);
            } else {
                texto.append(caractereCifrado); 
            }
        }

        return texto.toString();
    }
    
    public static void analise(String textoCriptografado) {;
        double[] frequenciasEsperadas = {14.63, 1.04, 3.88, 4.99, 12.57, 1.02, 1.30, 1.28, 6.18, 0.40, 0.02, 2.78,
                4.74, 5.05, 10.73, 2.52, 1.20, 6.53, 7.81, 4.34, 4.63, 1.67, 0.01, 0.21, 0.01, 0.47};

        Map<Character, Integer> frequenciasObservadas = new HashMap<>();
        int totalLetras = 0;
        for (char letra : textoCriptografado.toCharArray()) {
            if (Character.isLetter(letra)) {
                frequenciasObservadas.put(letra, frequenciasObservadas.getOrDefault(letra, 0) + 1);
                totalLetras++;
            }
        }

        double somaDiferencas = 0.0;
        for (char letra = 'a'; letra <= 'z'; letra++) {
            double frequenciaObservada = (double) frequenciasObservadas.getOrDefault(letra, 0) / totalLetras * 100;
            double diferenca = Math.abs(frequenciaObservada - frequenciasEsperadas[letra - 'a']);
            somaDiferencas += diferenca;
        }
        
        int deslocamentoEstimado = (int) Math.round(somaDiferencas);

        System.out.println("Deslocamento estimado: " + deslocamentoEstimado);
    }

    public static void main(String[] args) {
        try {
            Scanner entrada = new Scanner(System.in);
            System.out.print("Informe o texto a ser criptografado: ");
            String texto = entrada.nextLine();
            System.out.print("Informe a chave de deslocamento: ");
            int chave = entrada.nextInt();

            String textoCriptografado = encriptar(chave, texto);
            String textoDescriptografado = decriptar(chave, textoCriptografado);

            System.out.println("\nTEXTO CRIPTOGRAFADO: " + textoCriptografado);
            System.out.println("TEXTO DESCRIPTOGRAFADO: " + textoDescriptografado);
            analise(textoCriptografado);
        } catch (RuntimeException e) {
            System.out.println("A chave de deslocamento foi informada incorretamente.");
            System.out.println("Execute o programa novamente e entre com uma chave válida.");
        }
    }


}
