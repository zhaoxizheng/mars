package mars;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import mars.common.ResponseHelper;
import mars.entity.HistoryItem;
import mars.entity.Item;
import mars.entity.LoginInfo;
import mars.service.TickerMasterService;


@Controller
public class ApiController {

	@Autowired
	private TickerMasterService service;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ModelAndView greeting() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", "First SpringMVC project");
		mv.setViewName("hello");
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public void search(@RequestParam(value = "user_id") String userId,
                       @RequestParam(value = "lat") double lat,
 	                   @RequestParam(value = "lon") double lon,
                       @RequestParam(value = "term") Optional<String> term,
			HttpServletRequest request, HttpServletResponse response) {
		List<Item> itemList = service.search(lat, lon, term.orElse(""));
		ResponseHelper.createResponse(response, itemList);
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public void getHistory(@RequestParam(value = "user_id") String userId,
                           HttpServletRequest request,
			               HttpServletResponse response) {
		Set<Item> items = service.getUserFavorites(userId);
		ResponseHelper.createResponse(response, items);
	}

	@RequestMapping(value = "/history", method = RequestMethod.POST)
	public void setUserHistory(@RequestBody HistoryItem historyItem,
                               HttpServletRequest request,
			                   HttpServletResponse response) {
		service.setUserFavorites(historyItem);
		ResponseHelper.getResponse(response, new JSONObject().put("result", "SUCCESS"));
	}
	@RequestMapping(value = "/history", method = RequestMethod.DELETE)
	public void deleteUserHistory(@RequestBody HistoryItem historyItem,
                                  HttpServletRequest request,
                               	  HttpServletResponse response) {
		service.deleteUserFavorites(historyItem);
		ResponseHelper.getResponse(response, new JSONObject().put("result", "SUCCESS"));
	}

	@RequestMapping(value = "/recommendation", method = RequestMethod.GET)
	public void getRecommendation(@RequestParam(value = "user_id") String userId,
			                      @RequestParam(value = "lat") double lat, 
			                      @RequestParam(value = "lon") double lon,
			                      HttpServletRequest request, HttpServletResponse response) {
		List<Item> itemList = service.getUserRecommendation(userId, lat, lon);
		ResponseHelper.createResponse(response, itemList);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject obj = new JSONObject();
			HttpSession session = request.getSession(false);
			if (session == null) {
				response.setStatus(403);
				obj.put("status", "Session Invalid");
			} else {
				String userId = (String) session.getAttribute("user_id");
				String name = service.getFullName(userId);
				obj.put("status", "OK");
				obj.put("user_id", userId);
				obj.put("name", name);
			}
			ResponseHelper.getResponse(response, obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void verifyLogin(@RequestBody LoginInfo loginInfo, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		if (service.verify(loginInfo)) {
			HttpSession session = request.getSession();
			String userId = loginInfo.getUser_id();
			session.setAttribute("user_id", userId);
			session.setMaxInactiveInterval(10 * 60);
			obj.put("status", "OK");
			obj.put("user_id", userId);
			obj.put("name", service.getFullName(userId));
		} else {
			obj.put("message", "unauthorized user");
			response.setStatus(401);
		}
		ResponseHelper.getResponse(response, obj);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logOut(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("index.html");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

