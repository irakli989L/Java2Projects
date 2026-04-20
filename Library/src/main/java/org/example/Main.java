package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.example.Servlets.BookServlet;
import org.example.Servlets.BorrowingServlet;
import org.example.Servlets.MemberServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(8080);
//        tomcat.setBaseDir("tomcat");
//
//        String docBase = new File("src/main/webapp").getAbsolutePath();
//
//        Context context = tomcat.addContext("", docBase);
//
//        Tomcat.addServlet(context, "default", "org.apache.catalina.servlets.DefaultServlet");
//        context.addServletMappingDecoded("/", "default");
//
//        Tomcat.addServlet(context, "bookServlet", new BookServlet());
//        context.addServletMappingDecoded("/books", "bookServlet");
//        context.addServletMappingDecoded("/books/*", "bookServlet");
//
//        Tomcat.addServlet(context, "memberServlet", new MemberServlet());
//        context.addServletMappingDecoded("/members", "memberServlet");
//        context.addServletMappingDecoded("/members/*", "memberServlet");
//
//        Tomcat.addServlet(context, "borrowServlet", new BorrowingServlet());
//        context.addServletMappingDecoded("/borrow", "borrowServlet");
//        context.addServletMappingDecoded("/borrow/*", "borrowServlet");
//
//        tomcat.start();
//        tomcat.getConnector();
//        System.out.println("server on http://localhost:8080");
//
//        tomcat.getServer().await();
    }
}