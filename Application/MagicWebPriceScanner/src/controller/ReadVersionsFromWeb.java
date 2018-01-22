package controller;
import model.AlmacenVersiones;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Almacen;

@SuppressWarnings("unused")
public class ReadVersionsFromWeb {
	// Arreglar
	final private static String regExpNumVersions = "Versiones\\:\\s(<([\\+\\w\\d\\s\\=\\\"\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*>){0,3}\\d+";
    
	public static void readVersionsFW(AlmacenVersiones actualCard) throws IOException {
        URL url = new URL(actualCard.getGeneralUrl());
        InputStream is =  url.openStream();
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            final Pattern numVersionsPattern = Pattern.compile(regExpNumVersions); 
            String regExpTotalVersions = "Hola soy relleno, por favor, quiereme :3"; // El contenido del string tiene que ser algo que no encontrarias jamás
            Pattern totalVersionsPattern = null;
            Matcher matcher;
            Matcher matcher2 = null;
            boolean encontrado = false;
            boolean encontrado2 = false;
            if((line = br.readLine()) != null) {
            	line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
            	line = Normalizer.normalize(line, Normalizer.Form.NFD);
            	line = line.replaceAll("\\p{M}", "");
            }
            while (line != null && (!encontrado || !encontrado2)) {
            	matcher = numVersionsPattern.matcher(line);              	
                if(matcher.find() && !encontrado) { 
                	actualCard.setNumVersiones(getNumVersionValue(matcher.group()));
                	regExpTotalVersions = "Apareci\\p{L}\\sen(<([\\+\\w\\d\\s\\=\\\"\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*>){" + actualCard.getNumVersiones()*4 + "}";
                	totalVersionsPattern = Pattern.compile(regExpTotalVersions); 
                	encontrado = true;
                }
                else {
                	if(encontrado) {
                    	matcher2 = totalVersionsPattern.matcher(line);
                        if(matcher2.find() && !encontrado2) { 
                        	getArrayVersiones(matcher2.group(), actualCard);
                        	encontrado2 = true;
                        }
                	}
                } 
                String auxLine = line;
                if((line = br.readLine()) != null) {
                	line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                	line = Normalizer.normalize(line, Normalizer.Form.NFD);
                	line = line.replaceAll("\\p{M}", "");
                }
                else { line = auxLine; }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("URL is malformed!!");
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
	}
	
    public static int getNumVersionValue(String stringToAnalize) {
    	String val = stringToAnalize.replaceAll("Versiones\\:\\s(<([\\+\\w\\d\\s\\=\\\\\"\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*>){0,3}", "");   	
    	int value = Integer.parseInt(val);
    	return value;
    }
    
    public static void getArrayVersiones(String stringVersiones, AlmacenVersiones actualCard) throws IOException {
    	String regExpHrefFilter = "href\\=\"([\\+\\w\\d\\s\\=\\\\\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*\"";
    	String versionToProcess;
    	final Pattern hrefFilterPattern = Pattern.compile(regExpHrefFilter);
    	Matcher hrefFilterMatcher = hrefFilterPattern.matcher(stringVersiones);
    	for(int i = 0; i < actualCard.getNumVersiones(); i++) {
	    	if(hrefFilterMatcher.find()) {
	    		versionToProcess = hrefFilterMatcher.group();
	    		versionToProcess = versionToProcess.replaceAll("href=", "");
	    		versionToProcess = versionToProcess.replaceAll("\"", "");
	    		actualCard.getVersiones().add(new Almacen("https://www.cardmarket.com" + versionToProcess));
	    	}
    	}
    }
}
