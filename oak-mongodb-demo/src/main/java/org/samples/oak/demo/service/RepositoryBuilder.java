package org.samples.oak.demo.service;

import java.net.UnknownHostException;

import javax.jcr.Repository;

import org.apache.jackrabbit.core.TransientRepository;

public class RepositoryBuilder {

    public static Repository getRepo(String host, final int port) throws UnknownHostException {
//        String uri = "mongodb://" + host + ":" + port;
//    	String uri ="mongodb://inferyx:inferyx#123@localhost:27017/framework_inferyx?authMechanism=SCRAM-SHA-1";
//        System.out.println(uri);
//        System.setProperty("oak.documentMK.disableLeaseCheck", "true");
//        System.setProperty("supportHighlighting", "true");
//        DocumentNodeStore ns = new DocumentMK.Builder().setMongoDB(uri, "oak_demo", 16).getNodeStore();
//        Repository repo = new Jcr(new Oak(ns)).createRepository();
//        System.out
//                .println("oak.documentMK.disableLeaseCheck=" + System.getProperty("oak.documentMK.disableLeaseCheck"));

    	return new TransientRepository();

//        return repo;
    }
}
