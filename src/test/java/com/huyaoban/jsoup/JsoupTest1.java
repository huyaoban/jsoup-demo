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
public class JsoupTest1 {

	@Test
	public void test1() {
		String html = "<html><head><title>First parse</title></head><body><p>Parse HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
	}

	@Test
	public void test2() {
		// 解析body片段
		String html = "<div><p>Lorem ipsum.</p>";
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();
	}

	@Test
	public void test3() throws IOException {
		Document doc = Jsoup.connect("http://www.baidu.com").get();
		String title = doc.title();
		log.info("title = {}", title);
	}

	@Test
	public void test4() {
		String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
		Document doc = Jsoup.parse(html);
		Element link = doc.select("a").first();

		String text = doc.body().text(); // An example link.
		log.info("text = {}", text);
		String linkHref = link.attr("href"); // http://example.com/
		log.info("linkHref = {}", linkHref);
		String linkText = link.text();// example
		log.info("linkText = {}", linkText);

		// 取得链接外的html
		String linkOuterHtml = link.outerHtml(); // <a href='http://example.com/'><b>example</b></a>
		log.info("linkOuterHtml = {}", linkOuterHtml);
		// 取的链接内的html
		String linkInnerHtml = link.html(); // <b>example</b>
		log.info("linkInnerHtml = {}", linkInnerHtml);
	}

	@Test
	public void test5() throws IOException {
		String url = "http://www.baidu.com";
		log.info("Fetching {}...", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		log.info("Media: {}", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img")) {
				log.info("* {}: <{}> {}x{} ({})", src.tagName(), src.attr("abs:src"), src.attr("width"),
						src.attr("height"), src.attr("alt").trim());
			} else {
				log.info("* {}: <{}>", src.tagName(), src.attr("abs:src"));
			}
		}

		log.info("Imports: {}", imports.size());
		for (Element link : imports) {
			log.info("* {} <{}> ({})", link.tagName(), link.attr("abs:href"), link.attr("rel"));
		}

		log.info("Links: {}", links.size());
		for (Element link : links) {
			log.info("* a: <{}> ({})", link.attr("abs:href"), link.text());
		}
	}
}
