package com.nkang.kxmoment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterDataRestController {
	@RequestMapping("masterdataread")
	public String getMasterData(@RequestParam(value="name", required=false) String name)
	{
		return "success";
	}
}