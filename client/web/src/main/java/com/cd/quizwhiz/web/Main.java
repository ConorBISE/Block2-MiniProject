package com.cd.quizwhiz.web;

import java.util.HashMap;
import java.util.Map;

import org.teavm.jso.dom.html.HTMLDocument;

import com.floreysoft.jmte.Engine;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Map<String, Object> model = new HashMap<>();
        model.put("address", "Filberttttt!");

        String template = "${if address}${address}${end}";
        String output = engine.transform(template, model);

        var document = HTMLDocument.current();
        var div = document.createElement("div");
        div.appendChild(document.createTextNode(output));
        document.getBody().appendChild(div);
    }
}
