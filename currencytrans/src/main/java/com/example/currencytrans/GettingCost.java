package com.example.currencytrans;

import android.os.AsyncTask;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GettingCost extends AsyncTask<Void, Void, String> {

    private TextView tv6;
    private String val;
    private String point;
    private Document doc;

    public GettingCost(TextView tv6, String val){
        this.tv6 = tv6;
        this.val = val;
    }

    @Override
    protected String doInBackground(Void... voids) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
        String content = "https://www.cbr.ru/scripts/XML_daily.asp?date_req="+date;
        getContent("https://www.cbr.ru/scripts/XML_daily.asp?date_req="+date);
        //System.out.println(content);
        return content;
    }

    protected void onPostExecute(String voids) {
        HashMap<String, NodeList> result = new HashMap();
        String[][] rates;

        doc.getDocumentElement().normalize();
        System.out.println(doc.getXmlVersion());
        NodeList nodeList = doc.getElementsByTagName("Valute");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node c = nodeList.item(i);
            NodeList nlChilds = c.getChildNodes();
            for (int j = 0; j < nlChilds.getLength(); j++) {
                if (nlChilds.item(j).getNodeName().equals("CharCode")) {
                    result.put(nlChilds.item(j).getTextContent(), nlChilds);
                }
            }
        }
        int k = 0;
        rates = new String[result.size()][2];

        for (Map.Entry<String, NodeList> entry : result.entrySet()) {
            NodeList temp = entry.getValue();
            double value = 0;
            int nominal = 0;
            for (int i = 0; i < temp.getLength(); i++) {
                if (temp.item(i).getNodeName().equals("Value")) {
                    value = Double.parseDouble(temp.item(i).getTextContent().replace(',', '.'));
                } else if (temp.item(i).getNodeName().equals("Nominal")) {
                    nominal = Integer.parseInt(temp.item(i).getTextContent());
                }
            }
            double amount = value / nominal;
            rates[k][0] = entry.getKey();
            rates[k][1] = (((double) Math.round(amount * 100)) / 100) + "";
            k++;
        }

        double values = 0;
        int nominals = 0;
        if (val.equals("RUB")) point = "1";
        else {
            for (Map.Entry<String, NodeList> entry : result.entrySet()) {
                NodeList temp = entry.getValue();
                if (entry.getKey().equals(val)) {
                    System.out.println(temp);
                    for (int i = 0; i < temp.getLength(); i++) {
                        if (temp.item(i).getNodeName().equals("Value")) {
                            values = Double.parseDouble(temp.item(i).getTextContent().replace(',', '.'));
                        } else if (temp.item(i).getNodeName().equals("Nominal")) {
                            nominals = Integer.parseInt(temp.item(i).getTextContent());
                        }
                    }
                }
            }

        double amounts = values / nominals;
        point = (((double) Math.round(amounts * 100)) / 100) + "";
    }
            tv6.setText(val +" "+ point);
    }

    private Document getContent(String content){
        try {
            URL url = new URL(content);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            doc = documentBuilder.parse(new InputSource(url.openStream()));
            return doc;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
