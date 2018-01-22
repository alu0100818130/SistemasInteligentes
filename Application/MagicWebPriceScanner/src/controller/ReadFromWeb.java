package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Almacen;

public class ReadFromWeb {
	// Las Regex van aqui
	final private static String regExpMinPrice = "De\\s\\(EX\\+\\)\\:(<([\\+\\w\\d\\s\\=\\\"\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*>){0,3}\\d+,\\d\\d";
    final private static String regExpTendPrice = "Tendencia\\sde\\sprecio\\:(<([\\+\\w\\d\\s\\=\\\"\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*>){0,3}\\d+,\\d\\d";
    final private static String regExpFoilPrice = "Foils\\sde\\:(<([\\+\\w\\d\\s\\=\\\"\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*>){0,3}\\d+,\\d\\d";
    
    public static void readFromWeb(Almacen actualCard) throws IOException {
        String version = actualCard.getUrl().replaceAll("https://www.cardmarket.com/es/Magic/Products/Singles/",  "");
        version = version.replaceAll("\\/.*", "");
        version = version.replaceAll("[\\+]", " ");
        actualCard.setExpansion(version.replaceAll("%3A", ""));
    	URL url = new URL(actualCard.getUrl());
        InputStream is =  url.openStream();
        try( BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            final Pattern minPattern = Pattern.compile(regExpMinPrice);
            final Pattern tendPattern = Pattern.compile(regExpTendPrice);
            final Pattern foilPattern = Pattern.compile(regExpFoilPrice);            
            boolean encontrado = false;
            boolean encontrado2 = false;
			boolean encontrado3 = false;
			line = new String(br.readLine().getBytes("ISO-8859-1"), "UTF-8");
            while (line != null && (!encontrado && !encontrado2 && !encontrado3)) {
                Matcher matcher = minPattern.matcher(line); 
                Matcher matcher2 = tendPattern.matcher(line); 
                Matcher matcher3 = foilPattern.matcher(line); 
                if(matcher.find() && !encontrado) { 
                	actualCard.setMinimoPrecio(getPriceValue(matcher.group())); 
                	encontrado = true;
                }
                if(matcher2.find() && !encontrado2) { 
                	actualCard.setTendenciaPrecio(getPriceValue(matcher2.group()));  
                	encontrado2 = true;
                }
                if(matcher3.find() && !encontrado3) { 
                	actualCard.setFoilPrecio(getPriceValue(matcher3.group())); 
                	encontrado3 = true;
                }
                if((line = br.readLine()) != null) {
                	line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                }
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
    
    public static double getPriceValue(String stringToAnalize) {
    	String val = stringToAnalize.replaceAll("[De\\s\\(EX\\+\\)\\:|Tendencia\\sde\\sprecio\\:|Foils\\sde\\:](<([\\+\\w\\d\\s\\=\\\"\\\\\\/\\_\\:\\;\\'\\(\\)\\.\\-\\%\\,\\p{L}])*>){0,3}", "");   	
    	val = val.replaceAll(",", ".");
    	double value = Double.parseDouble(val);
    	return value;
    }
}
