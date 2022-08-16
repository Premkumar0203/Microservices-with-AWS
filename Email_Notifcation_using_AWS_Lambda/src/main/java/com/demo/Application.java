package com.demo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.models.BookPurchase;

@SpringBootApplication
public class Application  {

	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);
	}

}
