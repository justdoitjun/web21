package com.kbstar.controller;

import com.kbstar.dto.Ncp;
import com.kbstar.util.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.attribute.standard.JobName;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@Component
public class NcpController {
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("/Users/junhyeokchoi/spring/uimg/")
    String imgpath;
    @Autowired
    CFRCelebrityUtil celebrityUtil;

    @Autowired
    CFRFaceUtil cfrFaceUtil;

    @RequestMapping("/cfr1impl")
    public String cfr1impl(Model model, Ncp ncp) throws Exception{
        //이미지 저장한다.
        FileUploadUtil.saveFile(ncp.getImg(),imgpath);
        //NCP에 요청한다.
        String imgname = ncp.getImg().getOriginalFilename();
        JSONObject result = (JSONObject) celebrityUtil.getResult(imgpath, imgname);
        JSONArray faces = (JSONArray)result.get("faces");
        JSONObject obj = (JSONObject)faces.get(0);
        JSONObject celebrity = (JSONObject) obj.get("celebrity");
        String value = (String) celebrity.get("value");
        log.info(value);
        model.addAttribute("name", value);

        model.addAttribute("center", "cfr1");
        //결과를 받는다.

        return "index";
    }
    @RequestMapping("/cfr2impl")
    public String cfr2impl(Model model, Ncp ncp) throws Exception{
        HashMap<String, String> map = new HashMap<>();
        //이미지 저장한다.
        try {
            FileUploadUtil.saveFile(ncp.getImg(), imgpath);
            //NCP에 요청한다.
            String imgname = ncp.getImg().getOriginalFilename();
            JSONObject result = (JSONObject) cfrFaceUtil.getResult(imgpath, imgname);
            log.info(String.valueOf(result));
            JSONArray faces = (JSONArray) result.get("faces");
            JSONObject obj = (JSONObject) faces.get(0);
            JSONObject emotion = (JSONObject) obj.get("emotion");
            JSONObject emotionValue = (JSONObject) emotion.get("value");
            JSONObject gender = (JSONObject) obj.get("gender");
            JSONObject genderValue = (JSONObject) obj.get("value");
            JSONObject pose = (JSONObject) obj.get("pose");
            JSONObject poseValue = (JSONObject) obj.get("value");


            map.put("emotion", emotionValue.toJSONString());
            map.put("gender", genderValue.toJSONString());
            map.put("pose", poseValue.toJSONString());
            log.info(emotionValue.toJSONString());
            log.info(genderValue.toJSONString());
            log.info( poseValue.toJSONString());


            //결과를 받는다.
        }catch(Exception e){
            e.printStackTrace();
        }
        model.addAttribute("result",map);
        model.addAttribute("center","cfr2");

        return "index";
    }

    @RequestMapping("/mycfr")
    public String mycfr(Model model, String imgname) throws Exception{
        //이미지 저장한다.
        HashMap<String, String> map = new HashMap<>();
            JSONObject result = (JSONObject) cfrFaceUtil.getResult(imgpath, imgname);
            log.info(String.valueOf(result));
            JSONArray faces = (JSONArray) result.get("faces");
            JSONObject obj = (JSONObject) faces.get(0);
            JSONObject emotion = (JSONObject) obj.get("emotion");
            JSONObject emotionValue = (JSONObject) emotion.get("value");
            JSONObject gender = (JSONObject) obj.get("gender");
            JSONObject genderValue = (JSONObject) obj.get("value");
            JSONObject pose = (JSONObject) obj.get("pose");
            JSONObject poseValue = (JSONObject) obj.get("value");


            map.put("emotion", emotionValue.toJSONString());
            map.put("gender", genderValue.toJSONString());
            map.put("pose", poseValue.toJSONString());



        model.addAttribute("result",map);
        model.addAttribute("center","pic");



        return "index";
    }

    @RequestMapping("/ocr1impl")
    public String ocr1impl(Model model, Ncp ncp) throws Exception{
        //이미지 저장한다.
        FileUploadUtil.saveFile(ncp.getImg(),imgpath);
        //NCP에 요청한다.
        String imgname = ncp.getImg().getOriginalFilename();
        JSONObject result = (JSONObject) OCRUtil.getResult(imgpath, imgname);
        Map<String, String> map = new HashMap<>();
        map = OCRUtil.getData(result);


//결과를 jsp에 뿌린다.
        model.addAttribute("result", map);

//결과를 받는다.
        model.addAttribute("center", "ocr1");
        return "index";
    }


    @RequestMapping("/ocrbapl1impl")
    public String ocrbapl1impl(Model model, Ncp ncp) throws Exception{
        //이미지 저장한다.
        FileUploadUtil.saveFile(ncp.getImg(),imgpath);
        //NCP에 요청한다.
        String imgname = ncp.getImg().getOriginalFilename();
        JSONObject result = (JSONObject) OCRBaplUtil.getResult(imgpath, imgname);
        Map<String, String> map = new HashMap<>();
        map = OCRBaplUtil.getData(result);

//결과를 jsp에 뿌린다.
        model.addAttribute("result", map);
//결과를 받는다.
        model.addAttribute("center", "ocrbapl1");
        return "index";
    }
}
