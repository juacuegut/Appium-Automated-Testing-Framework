package com.qustodio.qustodioapp.testData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataObject {
    private String email;
    private String password;
    private String deviceName;
    private String whoUsesThisDevice;

    // Constructor, getters y setters

    // Constructor vacío para que Jackson pueda deserializar el JSON
    public DataObject() {
    }

    // Getters y setters para los campos
    // ...
}
