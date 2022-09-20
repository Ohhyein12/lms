package kr.co.gdu.lms.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.gdu.lms.log.CF;
import kr.co.gdu.lms.service.LoginService;
import kr.co.gdu.lms.service.MemberService;
import kr.co.gdu.lms.vo.Login;
import kr.co.gdu.lms.vo.MemberFile;
import kr.co.gdu.lms.vo.MemberForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired private LoginService loginService;
	@Autowired private MemberService memberService;
	
	@GetMapping("/accountRecovery") 
	public String accountRecovery(@RequestParam(name = "loginId") String loginId) {
		loginService.modifyActiveByMember(loginId);
		return "redirect:/login";
	}
	
	@GetMapping("/changePw")
	public String changePw(@RequestParam(value = "changePwLater", defaultValue = "") String changePwLater
						, @RequestParam(value = "loginId") String loginId)  {
		
		log.debug(CF.LCH+"LoginController.changePw.get changePwLater : "+changePwLater+CF.RS);
		
		if("on".equals(changePwLater)) {
			loginService.modifyLastChangePwDate(loginId);
			log.debug(CF.LCH+"LoginController.changePw.get 3개월 연장"+CF.RS);
			return "redirect:/loginCheck/main";
		}
		
		log.debug(CF.LCH+"LoginController.changePw.get 비밀번호 변경"+CF.RS);
		return "redirect:/modifyLoginPw?loginId="+loginId;
	}
	
	@GetMapping("/loginCheck/modifyAddMemberActiveDenied")
	public String modifyAddMemberActiveDenied(@RequestParam(name="loginId") String loginId) {
		
		log.debug(CF.LCH + "LoginController.modifyAddMemberActiveDenied.get loginId : " + loginId + CF.RS);
		
		loginService.modifyAddMemberActiveDenied(loginId);
		
		return "redirect:/loginCheck/acceptAddMember";
	}
	
	@GetMapping("/loginCheck/modifyAddMemberActive")
	public String modifyAddMemberActive(@RequestParam(name="loginId") String loginId) {
		
		log.debug(CF.LCH + "LoginController.modifyAddMemberActive.get loginId : " + loginId + CF.RS);
		
		loginService.modifyAddMemberActive(loginId);
		
		return "redirect:/loginCheck/acceptAddMember";
	}
	
	@GetMapping("/loginCheck/acceptAddMember")
	public String acceptAddMember(Model model) {
		
		Map<String, Object> returnMap = loginService.acceptAddMember();
		
		log.debug(CF.LCH + "LoginController.acceptAddMemeber.get returnMap : " + returnMap + CF.RS);
		
		model.addAttribute("managerList", returnMap.get("managerList"));
		model.addAttribute("teacherList", returnMap.get("teacherList"));
		model.addAttribute("studentList", returnMap.get("studentList"));
		
		return "login/acceptAddMemberList";
	}

	// 비밀번호 변경 액션
	@PostMapping("/modifyLoginPw")
	public String modifyLoginPw(Login login) {
		
		log.debug(CF.PHW+"LoginController.modifyLoginPw.post login : "+login+CF.RS );
		
		loginService.modifyLoginPw(login);
		loginService.addPwRecord(login);
		
		return "redirect:/login";
	}
	
	// 비밀번호 변경 폼
	@GetMapping("/modifyLoginPw")
	public String modifyLoginPw(Model model
								, @RequestParam (name = "loginId") String loginId) {
		
		log.debug(CF.PHW+"LoginController.modifyLoginPw.get loginId : "+loginId+CF.RS );

		model.addAttribute("loginId", loginId);
		
		return "login/modifyLoginPw";
	}
	
	// 비밀번호 찾기(확인)
	@PostMapping("/searchLoginPw")
	public String searchLoginPw(Model model
								, @RequestParam (name = "msg") String msg
								, @RequestParam (name = "loginId") String loginId
								, @RequestParam (name = "name") String name
								, @RequestParam (name = "email") String email) {
		
		log.debug(CF.PHW+"LoginController.searchLoginPw.post msg : "+msg+CF.RS );
		log.debug(CF.PHW+"LoginController.searchLoginPw.post loginId : "+loginId+CF.RS );
		log.debug(CF.PHW+"LoginController.searchLoginPw.post name : "+name+CF.RS );
		log.debug(CF.PHW+"LoginController.searchLoginPw.post email : "+email+CF.RS );
		
		Map<String, Object> map = new HashMap<>();
		map.put("loginId", loginId);
		map.put("name", name);
		map.put("email", email);
		map.put("msg", msg);
		
		
		int cnt = loginService.searchAllLoginPw(map);
		log.debug(CF.PHW+"LoginController.searchLoginPw.post cnt : "+cnt+CF.RS );
		
		if(cnt == 0) {
			return "redirect:/searchLoginPw";
		}
		
		model.addAttribute("loginId", loginId);
		
		return "login/modifyLoginPw";
		
	}
	
	
	// 학생, 강사, 매니저 비밀번호 찾기 폼
	@GetMapping("/searchLoginPw")
	public String searchLoginPw(Model model
								, @RequestParam (name = "msg", defaultValue = "student") String msg) {
		
		log.debug(CF.PHW+"LoginController.searchLoginPw.get msg : "+msg+CF.RS );

		model.addAttribute("msg", msg);

		return "login/searchLoginPw";

	}
	
	// 학생, 강사, 매니저 아이디 찾기 액션
	@PostMapping("/searchLoginId")
	public String searchLoginId(Model model
								, @RequestParam (name = "msg") String msg
								, @RequestParam (name = "name") String name
								, @RequestParam (name = "email") String email) {
		
		log.debug(CF.PHW+"LoginController.searchLoginId.post msg : "+msg+CF.RS );
		log.debug(CF.PHW+"LoginController.searchLoginId.post name : "+name+CF.RS );
		log.debug(CF.PHW+"LoginController.searchLoginId.post email : "+email+CF.RS );
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("email", email);
		map.put("msg", msg);
		
		String resultMsg = loginService.searchAllLoginId(map);
		log.debug(CF.PHW+"LoginController.searchLoginId.post resultMsg : "+resultMsg+CF.RS);
		
		model.addAttribute("resultMsg", resultMsg);
		
		return "login/searchLoginId";
	}

	
	// 학생, 강사, 매니저 아이디 찾기 폼
	@GetMapping("/searchLoginId")
	public String searchLoginId(Model model
								, @RequestParam(value = "msg", defaultValue = "student") String msg) {
		
		log.debug(CF.PHW+"LoginController.searchLoginId.get msg : "+msg+CF.RS );
		
		model.addAttribute("msg", msg);
		
		return "login/searchLoginId";
	}
	
	
	// 회원가입 폼
	@GetMapping("/addMember")
	public String addMemeber(Model model
							, @RequestParam(value="addChk", defaultValue="student") String addChk) {
				
		//디버깅
		log.debug(CF.OHI+"LoginController.addMember addChk : "+addChk+CF.RS);
		
		if("manager".equals(addChk)) {
			Map<String, Object> map = loginService.addMemberGetDeptAndPosition();
			log.debug(CF.OHI+"LoginController.addMember.map : "+map+CF.RS);
			
			model.addAttribute("dept",map.get("dept"));
			model.addAttribute("position",map.get("position"));
		}
		
		model.addAttribute("addChk",addChk);
		return "login/addMember";
	}
	
	// 회원가입 액션
	@PostMapping("/addMember")
	public String addMember(HttpServletRequest request
							, MemberForm memberForm) {
		
		String path = request.getServletContext().getRealPath("/file/memberPhoto/");
		// 디버깅
		log.debug(CF.OHI+"LoginController.addMember.Post path : "+path+CF.RS);
		log.debug(CF.OHI+"LoginController.addMember.Post login : "+memberForm+CF.RS);
		// 사진 이름 디버깅
		log.debug(CF.OHI+"LoginController.addMember.Post fileName : "+memberForm.getCustomFile().getOriginalFilename());
		
		loginService.addMember(memberForm, path);
		
		// 회원 가입 성공했다면 login페이지로
		return "redirect:/login";
	}
	
	// 로그아웃
	@GetMapping("/loginCheck/logout")
	public String logout(HttpSession session) {
		
		// 로그아웃하면 세션 끊기
		session.invalidate();
		
		return "redirect:/login";
	}
	
	// 메인페이지
   @GetMapping("/loginCheck/main") 
   public String main(Model model, HttpSession session) {
      
      Login login = new Login();
      
      // 세션을 통해 로그인ID 받아오기
      String loginId = (String) session.getAttribute("sessionId");
      log.debug(CF.GDH + "LoginController.main loginId : " + loginId + CF.RS);
      
      // 레벨 받아오기
      int level = (int)session.getAttribute("sessionLv");
      log.debug(CF.GDH + "LoginController.main level : " + level + CF.RS);
         
      login.setLoginId(loginId);
      login.setLevel(level);
      
      // 멤버사진 받아오기
      Map<String, Object> returnMap = memberService.getMemberOne(login);
      log.debug(CF.GDH + "LoginController.main returnMap : " + returnMap + CF.RS);
      if(level != 4) {
    	  MemberFile memberFile = (MemberFile)returnMap.get("memberFile");
    	  String memberFileName = memberFile.getMemberFileName();
    	  session.setAttribute("memberFileName", memberFileName);
    	  model.addAttribute("memberFile", memberFile);
      }
      
      //과목 리스트 받아오기
      List<Map<String,Object>> lectureList = loginService.addLectureList();
      for(Map m : lectureList) {
    	  log.debug(CF.GDH+(String) m.get("lectureName")+CF.RS);
      }
      LocalDate date = LocalDate.now();
      String nowDate = date.toString().replace("-", "");
      String year = nowDate.substring(0,4);
      String month = null;
      if(nowDate.substring(4,5).equals("0")) {
         month = nowDate.substring(5,6);
      } else {
         month = nowDate.substring(4,6);
      }
      String day = nowDate.substring(6,8);
      
      int week = date.getDayOfWeek().getValue();
      String dayOfWeek = null;
      
      if(week == 1) {
         dayOfWeek = "월요일";
      } else if(week == 2) {
         dayOfWeek = "화요일";
      } else if(week == 3) {
         dayOfWeek = "수요일";
      } else if(week == 4) {
         dayOfWeek = "목요일";
      } else if(week == 5) {
         dayOfWeek = "금요일";
      } else if(week == 6) {
         dayOfWeek = "토요일";
      } else {
         dayOfWeek = "일요일";
      }
      
      log.debug(CF.GDH + "LoginController.main.get loginId : " + loginId + CF.RS);
      log.debug(CF.LCH + "LoginController.main.get year : " + year + CF.RS);
      log.debug(CF.LCH + "LoginController.main.get month : " + month + CF.RS);
      log.debug(CF.LCH + "LoginController.main.get day : " + day + CF.RS);
      log.debug(CF.LCH + "LoginController.main.get dayOfWeek : " + dayOfWeek + CF.RS);
      
      
      model.addAttribute("lectureList",lectureList);
      model.addAttribute("year", year);
      model.addAttribute("month", month);
      model.addAttribute("day", day);
      model.addAttribute("dayOfWeek", dayOfWeek);
      
      return "login/main";
   }
	
	// 로그인 폼
	@GetMapping("/login") 
	public String login(Model model
						, HttpServletRequest request
						, @RequestParam(value = "msg2", defaultValue = "true") String msg2) {
		
		model.addAttribute("msg2", msg2);
		log.debug(CF.LCH+"LoginController.login.get msg2 : " + msg2 + CF.RS);
		String cookieId = null; // model에 담을 cookieId 선언 
		Cookie[] cookies = null;
		if(request.getCookies() != null) {
			 cookies = request.getCookies();
			for(Cookie c : cookies) { // 쿠키 배열들 돌리면서
				if("cookieId".equals(c.getName())) { // cookieId라는 이름 가진 쿠키 값 있다면
					cookieId = c.getValue(); // 쿠키아이디에 정보값 담아주기
				}
			}
			log.debug(CF.OHI+"LoginController.login.get cookieId : "+cookieId+CF.RS);
			
			model.addAttribute("cookieId",cookieId);
		}
		
		return "login/login";
	}
	
	// 로그인 액션
	@PostMapping("/login")
	public String login(Model model
						, HttpSession session
						, HttpServletResponse response
						, Login loginTest
						, @RequestParam(value="idSave", defaultValue="") String idSave) {
		
		log.debug(CF.OHI+"LoginController.login.post login : "+loginTest+CF.RS);
		log.debug(CF.OHI+"LoginController.login.post idSave : "+idSave+CF.RS);
		
		// 비활성화 계정 중 6개월 이내에 로그인하면 활성화 할 지 물어보기
		List<Map<String, Object>> recoveryMemberList = loginService.getRecoveryMember();
		for(Map m : recoveryMemberList) {
			if(m.get("loginId").equals(loginTest.getLoginId())) {
				model.addAttribute("lastLoginDate", m.get("lastLoginDate"));
				model.addAttribute("loginId", m.get("loginId"));
				return "login/accountRecovery";
			}
		}
		
		// 비밀번호 변경 며칠이 지났는지
		int diffDay = loginService.getDiffDay(loginTest.getLoginId());
		log.debug(CF.LCH + "LoginController.login.post diffDay : " + diffDay + CF.RS);
		
		// 비밀번호 변경 90일이 지났을 때
		if(diffDay > 90) {
			String msg = "changePw"; // changePw 메세지 보내기
			// 정보 보내기
			model.addAttribute("loginId", loginTest.getLoginId());
			model.addAttribute("msg", msg);
			// 로그인이 성공했는지 판별하기 위한 서비스 호출
			Login login = loginService.getLoginLevel(loginTest);
			if(login == null) { // 로그인 실패 시
				return "redirect:/login"; //다시 로그인 페이지로
			}
			// 로그인 성공 시
			return "login/login";
		}
		
		// 로그인 정보 넣어서 맞다면 로그인아이디와 level 들고오기   
		Login login = loginService.login(loginTest);
		
		int loginActive = loginService.getLoginActive(loginTest);
		log.debug(CF.LCH+"LoginController.login.post loginActive"+loginActive+CF.RS);
		if(loginActive != 1) {
			model.addAttribute("loginActive", loginActive);
			return "login/login";
		}
		
		if(login == null) { // 로그인 정보가 일치하지 않으면
			log.debug(CF.OHI+"LoginController.login.post 로그인 정보가 일치하지 않습니다"+login+CF.RS);
			return "redirect:/login?msg2=fail"; //다시 로그인 페이지로
		}
		
		if("on".equals(idSave)) {
			Cookie cookieId = new Cookie("cookieId", login.getLoginId()); // 현재 접속된 로그인 아이디 쿠키아이디값으로 저장
			cookieId.setMaxAge(60*60*24); // 쿠키 생명주기 하루로 설정
			response.addCookie(cookieId); // response에 쿠키 저장
		}
		
		// 세션에 로그인아이디와 로그인레벨 담기
		session.setAttribute("sessionId" , login.getLoginId());
		session.setAttribute("sessionLv" , login.getLevel());
		
		return "redirect:/loginCheck/main"; // 메인으로
	}
}
