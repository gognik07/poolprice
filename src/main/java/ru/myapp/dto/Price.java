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

    public Price() {
    }

    public Price(long id, String productCode, int number, int depart, Date begin, Date end, long value) {
        this.id = id;
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public Price(Price price) {
        this.id = price.id;
        this.productCode = price.productCode;
        this.number = price.number;
        this.depart = price.depart;
        this.begin = price.begin;
        this.end = price.end;
        this.value = price.value;
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
