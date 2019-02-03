package p5skeleton;

import p6datacollection.AddressCollection;

public class Provider {
    private int providerId;
    private String name;
    private String fiscalCode;
    private String tradeRegister;
    private int addressId;
    private int capital;
    private String phone;
    private String bank;
    private String account;
    private int VATRate;

    public Provider(int providerId, String name, String fiscalCode,
                    String tradeRegister, int addressId, int capital,
                    String phone, String bank, String account, int VATRate) {
        this.providerId = providerId;
        this.name = name;
        this.fiscalCode = fiscalCode;
        this.tradeRegister = tradeRegister;
        this.addressId = addressId;
        this.capital = capital;
        this.phone = phone;
        this.bank = bank;
        this.account = account;
        this.VATRate = VATRate;
    }

    public Provider(){}

    public int getProviderId() {
        return providerId;
    }
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }
    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCapital() {
        return capital;
    }
    public void setCapital(int capital) {
        this.capital = capital;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }

    public int getVATRate() {
        return VATRate;
    }
    public void setVATRate(int VATRate) {
        this.VATRate = VATRate;
    }

    @Override
    public String toString() {
        AddressCollection addressList = new AddressCollection();
        Address center = addressList.getAddressWithId(addressId);

        StringBuilder sb = new StringBuilder();
        sb.append("Furnizor:       ").append(name).append("\n");
        sb.append("Cod Fiscal:     ").append(fiscalCode).append("\n");
        sb.append("Reg. Com.       ").append(tradeRegister).append("\n");
        sb.append("Sediul:         ").append(center.getStreet() + ", " + center.getNumber() + ", ").append("\n");
        sb.append("                ").append(center.getCity() + ", " +center.getCountry() + ", " + center.getZipCode()).append("\n");
        sb.append("Capital:        ").append(capital + " RON").append("\n");
        sb.append("Telefon:        ").append(phone).append("\n");
        sb.append("Banca:          ").append(bank).append("\n");
        sb.append("Contul:         ").append(account).append("\n");
        sb.append("Cota T.V.A.:    ").append(VATRate + "%").append("\n");

        return sb.toString();
    }
}
