package ru.myapp.dto;

import java.util.Date;

/**
 * Цена
 */
public class Price {
    /**
     * Идентификатор в БД
     */
    private long id;

    /**
     * Код товара
     */
    private String productCode;

    /**
     * Номер цены
     */
    private int number;

    /**
     * Номер отдела
     */
    private int depart;

    /**
     * Начало действия
     */
    Date begin;

    /**
     * Конец действия
     */
    Date end;

    /**
     * Значение цены в копейках
     */
    long value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDepart() {
        return depart;
    }

    public void setDepart(int depart) {
        this.depart = depart;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
