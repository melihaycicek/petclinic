package com.veterinary.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class MainController {
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";



	@RequestMapping("/")
	public String index(Map<String, Object> map) {
	
		map.put("title", "Veteriner Anasayfası");
		return "index";
	}

	@RequestMapping("/index")
	public String index2(Map<String, Object> map) {
		map.put("title", "Veteriner Anasayfası");
		return "index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model, Map<String, Object> map) {
		map.put("title", "Doktor Kayıt Sayfası");
		return "register";
	}

	
	@RequestMapping("/login")
	public String login(Map<String, Object> map) {
		map.put("title", "Doktor Giriş Sayfası");
		return "login";
	}

	@RequestMapping("/admin-panel")
	public String secure(Map<String, Object> map) {

		map.put("title", "Yönetim Paneli");
		return "admin-panel";
	}
	@ResponseBody
	@RequestMapping("/index/data/{data}/")
	public String loginsss(@PathVariable String data,Map<String, Object> map) {
		
		return "data : "+data;
	}
}
