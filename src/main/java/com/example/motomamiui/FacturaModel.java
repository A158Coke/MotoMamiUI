package com.example.motomamiui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FacturaModel {
    private final SimpleStringProperty companyCIF;
    private final SimpleStringProperty companyAddress;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty date;
    private final SimpleStringProperty insuranceCompany;
    private final SimpleDoubleProperty vat;
    private final SimpleDoubleProperty totalCost;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty userAddress;
    private final SimpleStringProperty vehicleType;
    private final SimpleStringProperty insuranceType;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty endDate;
    private final SimpleDoubleProperty cost;

    // Constructor
    public FacturaModel(Integer id, String date, String insuranceCompany, Double vat,
                        Double totalCost, String firstName, String lastName, String userAddress,
                        String vehicleType, String insuranceType, String startDate, String endDate,
                        Double cost) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.insuranceCompany = new SimpleStringProperty(insuranceCompany);
        this.vat = new SimpleDoubleProperty(vat);
        this.totalCost = new SimpleDoubleProperty(totalCost);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userAddress = new SimpleStringProperty(userAddress);
        this.vehicleType = new SimpleStringProperty(vehicleType);
        this.insuranceType = new SimpleStringProperty(insuranceType);
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.cost = new SimpleDoubleProperty(cost);
        this.companyCIF = new SimpleStringProperty("41256985632");
        this.companyAddress = new SimpleStringProperty("C/ Vergel, 5 Madrid, 28080");
    }

    // Getters
    public Integer getId() { return id.get(); }
    public String getDate() { return date.get(); }
    public String getInsuranceCompany() { return insuranceCompany.get(); }
    public Double getVat() { return vat.get(); }
    public Double getTotalCost() { return totalCost.get(); }
    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public String getUserAddress() { return userAddress.get(); }
    public String getVehicleType() { return vehicleType.get(); }
    public String getInsuranceType() { return insuranceType.get(); }
    public String getStartDate() { return startDate.get(); }
    public String getEndDate() { return endDate.get(); }
    public Double getCost() { return cost.get(); }

    // Property getters
    public SimpleIntegerProperty idProperty() { return id; }
    // ... 其他属性的property getters

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleStringProperty insuranceCompanyProperty() {
        return insuranceCompany;
    }

    public SimpleDoubleProperty vatProperty() {
        return vat;
    }

    public SimpleDoubleProperty totalCostProperty() {
        return totalCost;
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public SimpleStringProperty userAddressProperty() {
        return userAddress;
    }

    public SimpleStringProperty vehicleTypeProperty() {
        return vehicleType;
    }

    public SimpleStringProperty insuranceTypeProperty() {
        return insuranceType;
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }

    public SimpleStringProperty endDateProperty() {
        return endDate;
    }

    public SimpleDoubleProperty costProperty() {
        return cost;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany.set(insuranceCompany);
    }

    public void setVat(double vat) {
        this.vat.set(vat);
    }

    public void setTotalCost(double totalCost) {
        this.totalCost.set(totalCost);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setUserAddress(String userAddress) {
        this.userAddress.set(userAddress);
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType.set(vehicleType);
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType.set(insuranceType);
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }

    public String getCompanyCIF() {
        return companyCIF.get();
    }

    public SimpleStringProperty companyCIFProperty() {
        return companyCIF;
    }

    public String getCompanyAddress() {
        return companyAddress.get();
    }

    public SimpleStringProperty companyAddressProperty() {
        return companyAddress;
    }

    public void setCompanyCIF(String companyCIF) {
        this.companyCIF.set(companyCIF);
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress.set(companyAddress);
    }
}
