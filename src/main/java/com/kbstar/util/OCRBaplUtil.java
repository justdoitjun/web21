package com.kbstar.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OCRBaplUtil {
    public static Object getResult(String imgpath, String imgname){
        JSONObject obj = null;

        String apiURL = "https://uhucar79op.apigw.ntruss.com/custom/v1/22517/b3c60e46e2433217e892560674224f9a6d08faef0ac65b5d9f6572ff06630406/infer";
        String secretKey = "bENoaVFWTG9Gb1RlWXVLeklReGJxaXREemhlRXlYUVc=";
        String imageFile = imgpath+imgname;

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod("POST");
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            long start = System.currentTimeMillis();
            File file = new File(imageFile);
            writeMultiPart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            JSONParser parser = new JSONParser();
            obj= (JSONObject) parser.parse(response.toString());

        } catch (Exception e) {
            System.out.println(e);
        }
        return obj;
    }


    private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
            IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        sb.append(jsonMessage);
        sb.append("\r\n");

        out.write(sb.toString().getBytes("UTF-8"));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }


    public static Map getData(JSONObject obj){
        Map <String, String> map = new HashMap<>();

        JSONArray images = (JSONArray) obj.get("images");
        JSONObject obj1 = (JSONObject) images.get(0);
        JSONArray fields = (JSONArray) obj1.get("fields");


        JSONObject field1Obj = (JSONObject) fields.get(0);
        String field1 = (String) field1Obj.get("inferText");
        JSONObject field2Obj = (JSONObject) fields.get(1);
        String field2 = (String) field2Obj.get("inferText");
        JSONObject field3Obj = (JSONObject) fields.get(2);
        String field3 = (String) field3Obj.get("inferText");
        JSONObject field4Obj = (JSONObject) fields.get(3);
        String field4 = (String) field4Obj.get("inferText");
        JSONObject field5Obj = (JSONObject) fields.get(4);
        String field5 = (String) field5Obj.get("inferText");
        JSONObject field6Obj = (JSONObject) fields.get(5);
        String field6 = (String) field6Obj.get("inferText");
        JSONObject field7Obj = (JSONObject) fields.get(6);
        String field7 = (String) field7Obj.get("inferText");
        JSONObject field8Obj = (JSONObject) fields.get(7);
        String field8 = (String) field8Obj.get("inferText");

        map.put("field1", field1);
        map.put("field2", field2);
        map.put("field3", field3);
        map.put("field4", field4);
        map.put("field5", field5);
        map.put("field6", field6);
        map.put("field7", field7);
        map.put("field8", field8);


        return map;
    }


}