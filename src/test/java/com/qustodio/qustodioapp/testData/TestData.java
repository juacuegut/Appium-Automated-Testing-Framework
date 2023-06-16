package com.qustodio.qustodioapp.testData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.DataProvider;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestData {

    @DataProvider(name = "testData")
    public static Object[] getTestData() {
        // Cargar el archivo JSON
        InputStream inputStream = TestData.class.getClassLoader().getResourceAsStream("testData.json");

        // Convertir el JSON en una lista de objetos Java
        ObjectMapper objectMapper = new ObjectMapper();
        List<DataObject> data = null;
        try {
            data = objectMapper.readValue(inputStream, new TypeReference<List<DataObject>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data.toArray();
    }
}

