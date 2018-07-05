package com.example.sara.marketer.model;

import com.example.sara.marketer.utils.Document;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sara on 8/29/17.
 */

public class Merchant implements Serializable {

    private long merchandId;

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    @SerializedName("national_code")
    @Expose
    private String nationalCode;
    @SerializedName("state")
    @Expose
    private int state;

    @SerializedName("sheba_number")
    private String shebaNumber;

    @SerializedName("address")
    private String address;

    @SerializedName("name")
    private String storeName;

    @SerializedName("description")
    private String description;


    @SerializedName("mobile_phone")
   private String cellNo;

    @SerializedName("docs_files")
    private List<Document> documentList;

//    String businessLicenceImage;
//
//    String nationalCardImage;

    @SerializedName("father_name")
   private String fathername;

    @SerializedName("id_number")
    private String identiricationId;

    @SerializedName("birth_date")
   private String birthdate;

    @SerializedName("birth_city")
   private String cityOfBirthCode;

//    public String getCityOfBirth1() {
//        return cityOfBirth1;
//    }
//
//    public void setCityOfBirth1(String cityOfBirth1) {
//        this.cityOfBirth1 = cityOfBirth1;
//    }

//    String cityOfBirth1;

    @SerializedName("phone")
   private String phoneNumer;

    @SerializedName("postal_code")
   private String postalCode;

    @SerializedName("bank_name")
   private String bankName;

    @SerializedName("bank_branch_name")
   private String branchName;

    @SerializedName("bank_branch_code")
   private String branchCode;

    @SerializedName("bank_account_type")
   private String accountType;

    @SerializedName("bank_account_number")
   private String accountNo;

    @SerializedName("website")
   private String webSite;

    @SerializedName("email_address")
    private String email;


   private String signature;

   private String barcode;

//    private  float[] location;
//
    float latitude;
    float longitude;

//
//  String businessLicenceImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getShebaNumber() {
        return shebaNumber;
    }

    public void setShebaNumber(String shebaNumber) {
        this.shebaNumber = shebaNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }


    public long getMerchandId() {
        return merchandId;
    }

    public void setMerchandId(long merchandId) {
        this.merchandId = merchandId;
    }

//    public String getBusinessLicenceImage() {
//        return businessLicenceImage;
//    }

//    public void setBusinessLicenceImage(String businessLicenceImage) {
//        this.businessLicenceImage = businessLicenceImage;
//    }

//    public String getNationalCardImage() {
//        return nationalCardImage;
//    }
//
//    public void setNationalCardImage(String nationalCardImage) {
//        this.nationalCardImage = nationalCardImage;
//    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getIdentiricationId() {
        return identiricationId;
    }

    public void setIdentiricationId(String identiricationId) {
        this.identiricationId = identiricationId;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCityOfBirthCode() {
        return cityOfBirthCode;
    }

    public void setCityOfBirthCode(String cityOfBirthCode) {
        this.cityOfBirthCode = cityOfBirthCode;
    }

    public String getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(String phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchandId=" + merchandId +
                ", id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", state=" + state +
                ", shebaNumber='" + shebaNumber + '\'' +
                ", address='" + address + '\'' +
                ", storeName='" + storeName + '\'' +
                ", description='" + description + '\'' +
                ", cellNo='" + cellNo + '\'' +
                ", documentList=" + documentList +
                ", fathername='" + fathername + '\'' +
                ", identiricationId='" + identiricationId + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", cityOfBirthCode='" + cityOfBirthCode + '\'' +
                ", phoneNumer='" + phoneNumer + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", webSite='" + webSite + '\'' +
                ", email='" + email + '\'' +
                ", signature='" + signature + '\'' +
                ", barcode='" + barcode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    //
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

//    public float[] getLocation() {
//        return location;
//    }
//
//    public void setLocation(float[] location) {
//        this.location = location;
//    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
