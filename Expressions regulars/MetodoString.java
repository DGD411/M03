import java.io.*;

public class MetodoString {
    public static void main(String[] args) throws IOException {

        String file = "santako.txt";
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = "";

        while ((line = br.readLine()) != null) {
            int papanoel = contarOcurrencias(line, "<]:-DOo");
            int reno = contarOcurrencias(line, ">:o)");
            int follet = contarOcurrencias(line, "<]:-D");

            StringBuilder salida = new StringBuilder();
            if (papanoel > 0) {
                salida.append("Pare Noel (").append(papanoel).append(") ");
            }
            if (reno > 0) {
                salida.append("Ren (").append(reno).append(") ");
            }
            if (follet > 0 && papanoel == 0 && reno == 0) {
                salida.append("Follet (").append(follet).append(") ");
            }

            System.out.println(salida.toString().trim());
        }
    }

    public static int contarOcurrencias(String line, String patron) {
        int contador = 0;
        int indice = 0;

        while ((indice = line.indexOf(patron, indice)) != -1) {
            contador++;
            indice++;
        }

        return contador;
    }
}