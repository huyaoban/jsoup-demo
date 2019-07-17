package com.huyaoban.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JsoupTest2 {

	@Test
	public void test1() throws IOException {
		String url = "https://www.oschina.net/project/list?company=0&sort=score&lang=0&recommend=false&p=1";

		Document doc = Jsoup.connect(url).userAgent(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
				.get();

		Element projectListEle = doc.getElementById("projectList");
		Elements projectList = projectListEle.select("div.item.project-item:not(.ad-wrap):not(.recommend-authors)");

		for (Element projEle : projectList) {
			Element headerEle = projEle.selectFirst("h3.header");
			Element descEle = projEle.selectFirst("div.description");
			Element extraEle = projEle.selectFirst("div.extra");

			Element projectLinkEle = headerEle.selectFirst("a");
			String projectLink = projectLinkEle.attr("href");
			log.info("projectLink = {}", projectLink);

			// Project Name和Project Title
			Element projectNameEle = headerEle.selectFirst("span.project-name");
			String projectName = projectNameEle.text();
			log.info("projectName = {}", projectName);
			Element projectTitleEle = headerEle.selectFirst("span.project-title");
			String projectTitle = projectTitleEle.text();
			log.info("projectTitle = {}", projectTitle);

			// 描述
			Element descriptionEle = descEle.selectFirst("p.line-clamp");
			String description = descriptionEle.text();
			log.info("description = {}", description);

			// 收藏/评论/更新时间
			Elements extraElemens = extraEle.select("div.item");
			String remarks = extraElemens.get(0).text();
			log.info("remarks = {}", remarks);
			String comments = extraElemens.get(1).text();
			log.info("comments = {}", comments);
			if (extraElemens.size() > 2) {
				String updatedAt = extraElemens.get(2).text();
				log.info("updatedAt = {}", updatedAt);
			}
		}
	}

}
