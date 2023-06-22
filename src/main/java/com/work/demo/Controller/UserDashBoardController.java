package com.work.demo.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.work.demo.Repository.CountRepository;
import com.work.demo.Repository.UserInteractionRepository;
import com.work.demo.model.Product;
import com.work.demo.model.UserInfo;
import com.work.demo.model.UserInteraction;
import com.work.demo.model.Usercount;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserDashBoardController {

	@Autowired
	private CountRepository cr;

	@Autowired
	private UserInteractionRepository uir;

	@GetMapping("/loaddashboard")
	public String loadDashBoard(HttpSession session, Model model) {
		UserInfo userinfo = (UserInfo) session.getAttribute("user");
		model.addAttribute("userinfo", userinfo);

		List<Usercount> uc = cr.findAll();

		List<Usercount> fuc = new ArrayList<>();
		for (Usercount u : uc) {
			if (u.getUserInfo().getId() == userinfo.getId()) {
				fuc.add(u);
			}
		}
		System.out.println(fuc.size());
		Collections.sort(fuc, Comparator.comparingInt(Usercount::getCount).reversed());
		int total = 0;
		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<UserInteraction> uin = uir.findAll();
		List<UserInteraction> filteredList = uin.stream()
				.filter(UserInteraction -> UserInteraction.getUserinfos().getId() == ui.getId())
				.collect(Collectors.toList());
		;

		System.out.println(filteredList.size());
		for (UserInteraction uiii : filteredList) {
			int a = uiii.getGainedpoints();
			total = total + a;
		}
		UserInfo uis = (UserInfo) session.getAttribute("user");
		List<Product> listorderplaced = uis.getOrderplaced();
		model.addAttribute("orderList", listorderplaced);

		System.out.println(total);

		model.addAttribute("total", total);
		model.addAttribute("fetchshop", fuc);

		return "UserPersonalDashBoard";
	}

	@GetMapping("/pointscalculate")
	public String loadThePoints(@RequestParam int id, HttpSession session, Model model) {
		int total = 0;
		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<UserInteraction> uin = uir.findAll();

		List<UserInteraction> filteredlist = uin.stream()
				.filter(UserInteraction -> UserInteraction.getUserinfos().getId() == ui.getId()
						&& UserInteraction.getProduct().getShop().getId() == id)
				.collect(Collectors.toList());

		for (UserInteraction uiii : uin) {
			int a = uiii.getGainedpoints();
			total = total + a;
		}

		System.out.println(total);
		model.addAttribute("message", "hello the points system is being implemented in the system");
		model.addAttribute("total", total);
		model.addAttribute("detail", filteredlist);
		return "DistributedPoints";
	}

	@GetMapping("/distributepointbyshop")
	public String getShopPoints(HttpSession session, Model model) {
		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<UserInteraction> uin = uir.findAll();

		for (UserInteraction U : uin) {
			U.setGainedpointsbyshop(0);
		}
		uir.saveAll(uin);

		Map<Integer, Integer> shopPointsMap = new HashMap<>();

		for (UserInteraction currentInteraction : uin) {
			int currentShopId = currentInteraction.getProduct().getShop().getId();
			if (!shopPointsMap.containsKey(currentShopId)) {
				shopPointsMap.put(currentShopId, 0);
			}
			int currentPoints = shopPointsMap.get(currentShopId);
			currentPoints += currentInteraction.getGainedpoints();
			shopPointsMap.put(currentShopId, currentPoints);
		}

		for (UserInteraction currentInteraction : uin) {
			int currentShopId = currentInteraction.getProduct().getShop().getId();
			if (shopPointsMap.containsKey(currentShopId)) {
				int finalPoints = shopPointsMap.get(currentShopId);
				currentInteraction.setGainedpointsbyshop(finalPoints);
			}
		}

		uir.saveAll(uin);

		List<UserInteraction> npl = new ArrayList<>();

		for (UserInteraction currentInteraction : uin) {
			int currentShopId = currentInteraction.getProduct().getShop().getId();
			boolean shopIdExists = false;

			for (UserInteraction existingInteraction : npl) {
				int existingShopId = existingInteraction.getProduct().getShop().getId();
				if (currentShopId == existingShopId) {
					shopIdExists = true;
					break;
				}
			}

			if (!shopIdExists) {
				npl.add(currentInteraction);
			}
		}

		List<UserInteraction> filteredList = npl.stream().filter(User -> User.getUserinfos().getId() == ui.getId())
				.collect(Collectors.toList());

		System.out.println(npl.size());

		model.addAttribute("List", filteredList);
		return "DistributedPointsByShop";
	}

	@GetMapping("/recentsorder")

	public String loadRecentOrder(HttpSession session, Model model) {
		UserInfo ui = (UserInfo) session.getAttribute("user");
		List<Product> listorderplaced = ui.getOrderplaced();
		model.addAttribute("orderList", listorderplaced);
		return "";
	}

}
