package com.example.assignment2.Entities;

public class Foods {
    private static final long serialVersionUID = 1L;

    private Integer foodid;

    private String foodname;

    private String category;

    private Integer calorieamount;

    private String servingunit;

    private Integer fat;

    private Integer servingamount;

    public Foods() {
    }

    public Foods(Integer foodid, String foodname, String category, Integer calorieamount, String servingunit, Integer fat, Integer servingamount) {
        this.foodid = foodid;
        this.foodname = foodname;
        this.category = category;
        this.calorieamount = calorieamount;
        this.servingunit = servingunit;
        this.fat = fat;
        this.servingamount = servingamount;
    }

    public Integer getFoodid() {
        return foodid;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCalorieamount() {
        return calorieamount;
    }

    public void setCalorieamount(Integer calorieamount) {
        this.calorieamount = calorieamount;
    }

    public String getServingunit() {
        return servingunit;
    }

    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getServingamount() {
        return servingamount;
    }

    public void setServingamount(Integer servingamount) {
        this.servingamount = servingamount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (foodid != null ? foodid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Foods)) {
            return false;
        }
        Foods other = (Foods) object;
        if ((this.foodid == null && other.foodid != null) || (this.foodid != null && !this.foodid.equals(other.foodid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "foodid= " + foodid;
    }

}

