package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Min(value = 1)
    @Max(value = 120)
    private int age;
}
