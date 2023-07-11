package com.digitalbooking.digitalbooking.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils {

   public Long gerarUid() {
      String digito = String.valueOf(new Random().nextInt(10));
            return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")).concat(digito));
   }
}
