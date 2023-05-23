package com.kbstar.controller;

import com.kbstar.dto.Cust;
import com.kbstar.service.CustService;
import com.kbstar.util.WeatherUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class MainController {
    @Value("${adminserver}")
    String adminServer;
    @Value("${serviceserver}")
    String serviceServer;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    CustService custService;

    @RequestMapping("/")
    public String main(Model model) throws Exception{
        //딱 들어오자마자 기상청 날씨 가져와라
        String result = WeatherUtil.getWeather1("109");
        model.addAttribute("weatherinfo", result);
        return "index";
    }
    //1996년도부터 쓴 방식.... quics?page=bs01   Get방식

    @RequestMapping("/pic")
    public String pic(Model model) {
        model.addAttribute("center",
                "pic");
        return "index";
    }

    @RequestMapping("/websocket")
    public String websocket(Model model) {
        model.addAttribute("adminServer", adminServer);
        model.addAttribute("center",
                "websocket");
        return "index";
    }
    @RequestMapping("/chatbot")
    public String chatbot(Model model, HttpSession session) {
//        if(session.getAttribute("logincust")== null){
//            return "redirect:/login";
//        }//로그인 안되어있으면 로그인하라고 돌려보내라. --> 지금은 db 연결이 문제가 있어서 주석처리함.
        model.addAttribute("adminServer", adminServer);
        model.addAttribute("center",
                "chatbot");
        return "index";
    }
    @RequestMapping("/callcenter")
    public String callcenter(Model model, HttpSession session) {
//        if(session.getAttribute("logincust")== null){
//            return "redirect:/login";
//        }//로그인 안되어있으면 로그인하라고 돌려보내라. --> 지금은 db 연결이 문제가 있어서 주석처리함.
        model.addAttribute("adminServer", adminServer);
        model.addAttribute("center",
                "callcenter");
        return "index";
    }
    @RequestMapping("/quics")
    public String quics(String page) {
        return page;
    }
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("center", "login");
        return "index";
    }
    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        if(session != null){
            session.invalidate(); //세션 정보 삭제
        }
        return "index";
    }
    @RequestMapping("/loginimpl")
    public String loginimpl(Model model, String id, String pwd,
                            HttpSession session) throws Exception {
        Cust cust = null;
        String nextPage = "loginfail";
        log.info("========================================");
        log.info(id);
        log.info(pwd);
        log.info("========================================");
        try{
            cust = custService.get(id); // 해당 id로 cust를 만든다.
            if(cust != null && encoder.matches(pwd,cust.getPwd())){   //DB에 아이디가 있다.
                nextPage = "loginok";
                session.setMaxInactiveInterval(1000000); //세션정보 저장시간
                session.setAttribute("logincust", cust); //세션정보 저장 loginCust라는 이름으로 Cust 정보 저장
            }
        }catch(Exception e){
            throw new Exception("System Error");
        }
        model.addAttribute("center", nextPage);
        return "index";
    }
    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("center", "register");
        return "index";
    }
    @RequestMapping("/registerimpl")
    public String registerImpl(Model model, Cust cust,
                               HttpSession session) throws Exception {
        try {
            cust.setPwd(encoder.encode(cust.getPwd()));
//            custService.register(cust);
//            session.setMaxInactiveInterval(10000);
            session.setAttribute("logincust", cust); //회원가입되면 바로 로그인될 수 있도록
        } catch (Exception e) {
            throw new Exception("가입 오류");
        }
        model.addAttribute("rcust", cust);
        model.addAttribute("center", "registerok");
        return "index";
    } //세션에 로그인 정보를 입력해주면, 회원가입을 하면 바로 login이 될 수 있도록 해주자.
    @RequestMapping(value ="/custinfo")
    public String custinfo(Model model, String id) throws Exception {
        Cust cust = null;
        try{
            cust = custService.get(id);
        }catch(Exception e) {
            throw new Exception("System Error");
        }
        model.addAttribute("custinfo", cust);
        model.addAttribute("center", "custinfo");
        return "index";
    }

    @RequestMapping("/custinfoimpl")
    public String custinfoimpl(Model model, Cust cust) throws Exception {
        try{
            log.info("----------------");
            log.info(cust.getPwd());
            cust.setPwd(encoder.encode(cust.getPwd())); //Password를 다시 encdoing 한 후
            custService.modify(cust); // Password를 다시 변경시키자.
        }catch(Exception e){
            throw new Exception("System Eroor");
        }
        //수정이 되었다면, custinfo Page로 다시 이동한다.
        return "redirect:/custinfo?id="+ cust.getId(); //다시 이동한다. 새로고침.
    }

    @RequestMapping("/cfr1")
    public String cfr1(Model model) {
        model.addAttribute("center",
                "cfr1");
        return "index";
    }

    @RequestMapping("/cfr2")
    public String cfr2(Model model) {
        model.addAttribute("center",
                "cfr2");
        return "index";
    }

    @RequestMapping("/ocr1")
    public String ocr1(Model model) {
        model.addAttribute("center",
                "ocr1");
        return "index";
    }

    @RequestMapping("/ocrbapl1")
    public String ocrbapl1(Model model) {
        model.addAttribute("center",
                "ocrbapl1");
        return "index";
    }


}
