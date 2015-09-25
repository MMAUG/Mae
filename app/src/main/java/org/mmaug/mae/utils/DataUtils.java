package org.mmaug.mae.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by poepoe on 15/9/15.
 */
public class DataUtils {

  private static DataUtils instance;
  private Context mContext;
  private Gson gson = new Gson();

  public DataUtils(Context context) {
    this.mContext = context;
  }

  public DataUtils() {

  }

  public static DataUtils getInstance(Context mContext) {
    if (instance == null) {
      instance = new DataUtils(mContext);
    }
    return instance;
  }

  public ArrayList<StateRegion> loadStateRegion() {
    ArrayList<StateRegion> stateRegions = new ArrayList<>();
    try {
      InputStream json = mContext.getAssets().open("Sate_Region.json");
      JsonParser parser = new JsonParser();
      JsonReader reader = new JsonReader(new InputStreamReader(json));
      reader.setLenient(true);

      JsonArray data = parser.parse(reader).getAsJsonArray();

      for (JsonElement element : data) {
        StateRegion stateRegion = gson.fromJson(element, StateRegion.class);
        stateRegions.add(stateRegion);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return stateRegions;
  }

  public ArrayList<District> loadDistrict() {
    ArrayList<District> districts = new ArrayList<>();
    try {
      InputStream json = mContext.getAssets().open("District.json");
      JsonParser parser = new JsonParser();
      JsonReader reader = new JsonReader(new InputStreamReader(json));
      reader.setLenient(true);

      JsonArray data = parser.parse(reader).getAsJsonArray();

      for (JsonElement element : data) {
        District district = gson.fromJson(element, District.class);
        districts.add(district);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return districts;
  }

  public ArrayList<Township> loadTownship() {
    ArrayList<Township> townships = new ArrayList<>();
    try {
      InputStream json = mContext.getAssets().open("Township.json");
      JsonParser parser = new JsonParser();
      JsonReader reader = new JsonReader(new InputStreamReader(json));
      reader.setLenient(true);

      JsonArray data = parser.parse(reader).getAsJsonArray();

      for (JsonElement element : data) {
        Township township = gson.fromJson(element, Township.class);
        townships.add(township);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return townships;
  }

  public ArrayList<Town> loadTown() {
    ArrayList<Town> stateRegions = new ArrayList<>();
    try {
      InputStream json = mContext.getAssets().open("Towns.json");
      JsonParser parser = new JsonParser();
      JsonReader reader = new JsonReader(new InputStreamReader(json));
      reader.setLenient(true);

      JsonArray data = parser.parse(reader).getAsJsonArray();

      for (JsonElement element : data) {
        Town town = gson.fromJson(element, Town.class);
        stateRegions.add(town);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return stateRegions;
  }

  public static class StateRegion {
    @SerializedName("SR_Pcode") private String SRPcode;
    @SerializedName("State_Region") private String SateRegionName;
    @SerializedName("State_Region_Mya_MM3") private String SateRegionNameBurmese;
    @SerializedName("MYAINFO_SD_ID") private String MyanInfoSdId;

    public String getSRPcode() {
      return SRPcode;
    }

    public void setSRPcode(String SRPcode) {
      this.SRPcode = SRPcode;
    }

    public String getSateRegionName() {
      return SateRegionName;
    }

    public void setSateRegionName(String sateRegionName) {
      SateRegionName = sateRegionName;
    }

    public String getSateRegionNameBurmese() {
      return SateRegionNameBurmese;
    }

    public void setSateRegionNameBurmese(String sateRegionNameBurmese) {
      SateRegionNameBurmese = sateRegionNameBurmese;
    }

    public String getMyanInfoSdId() {
      return MyanInfoSdId;
    }

    public void setMyanInfoSdId(String myanInfoSdId) {
      MyanInfoSdId = myanInfoSdId;
    }
  }

  public static class District {
    @SerializedName("SR_Pcode") private String SRPcode;
    @SerializedName("State_Region") private String SateRegionName;
    @SerializedName("D_Pcode") private String DPcode;
    @SerializedName("District") private String DistrictName;
    @SerializedName("District_Mya_MM3") private String DistrictNameBurmese;
    @SerializedName("MYAINFO_D_ID") private String MyanInfoDId;

    public String getSRPcode() {
      return SRPcode;
    }

    public void setSRPcode(String SRPcode) {
      this.SRPcode = SRPcode;
    }

    public String getSateRegionName() {
      return SateRegionName;
    }

    public void setSateRegionName(String sateRegionName) {
      SateRegionName = sateRegionName;
    }

    public String getDPcode() {
      return DPcode;
    }

    public void setDPcode(String DPcode) {
      this.DPcode = DPcode;
    }

    public String getDistrictName() {
      return DistrictName;
    }

    public void setDistrictName(String districtName) {
      DistrictName = districtName;
    }

    public String getDistrictNameBurmese() {
      return DistrictNameBurmese;
    }

    public void setDistrictNameBurmese(String districtNameBurmese) {
      DistrictNameBurmese = districtNameBurmese;
    }

    public String getMyanInfoDId() {
      return MyanInfoDId;
    }

    public void setMyanInfoDId(String myanInfoSdId) {
      MyanInfoDId = myanInfoSdId;
    }
  }

  public static class Township {
    @SerializedName("SR_Pcode") private String SRPcode;
    @SerializedName("State_Region") private String SateRegionName;
    @SerializedName("D_Pcode") private String DPcode;
    @SerializedName("District") private String District;
    @SerializedName("TS_Pcode") private String TSPcode;
    @SerializedName("Township") private String TownshipName;
    @SerializedName("Township_Mya_MM3") private String TowhshipNameBurmese;
    @SerializedName("MYAINFO_TS_ID") private String MyanInfoTsId;

    public String getSRPcode() {
      return SRPcode;
    }

    public void setSRPcode(String SRPcode) {
      this.SRPcode = SRPcode;
    }

    public String getSateRegionName() {
      return SateRegionName;
    }

    public void setSateRegionName(String sateRegionName) {
      SateRegionName = sateRegionName;
    }

    public String getDPcode() {
      return DPcode;
    }

    public void setDPcode(String DPcode) {
      this.DPcode = DPcode;
    }

    public String getDistrict() {
      return District;
    }

    public void setDistrict(String district) {
      District = district;
    }

    public String getTSPcode() {
      return TSPcode;
    }

    public void setTSPcode(String TSPcode) {
      this.TSPcode = TSPcode;
    }

    public String getTownshipName() {
      return TownshipName;
    }

    public void setTownshipName(String townshipName) {
      TownshipName = townshipName;
    }

    public String getTowhshipNameBurmese() {
      return TowhshipNameBurmese;
    }

    public void setTowhshipNameBurmese(String towhshipNameBurmese) {
      TowhshipNameBurmese = towhshipNameBurmese;
    }

    public String getMyanInfoTsId() {
      return MyanInfoTsId;
    }

    public void setMyanInfoTsId(String myanInfoTsId) {
      MyanInfoTsId = myanInfoTsId;
    }
  }

  public static class Town {
    @SerializedName("SR_Pcode") private String SRPcode;
    @SerializedName("State_Region") private String SateRegionName;
    @SerializedName("D_Pcode") private String DPcode;
    @SerializedName("District") private String District;
    @SerializedName("TS_Pcode") private String TSPcode;
    @SerializedName("Township") private String TownshipName;
    @SerializedName("Town_Pcode") private String TownPcode;
    @SerializedName("Town") private String TownName;
    @SerializedName("Town_Mya_MMR3") private String TownNameBurmese;
    @SerializedName("Longitude") private long lng;
    @SerializedName("Latitude") private long lat;

    public String getSRPcode() {
      return SRPcode;
    }

    public void setSRPcode(String SRPcode) {
      this.SRPcode = SRPcode;
    }

    public String getSateRegionName() {
      return SateRegionName;
    }

    public void setSateRegionName(String sateRegionName) {
      SateRegionName = sateRegionName;
    }

    public String getDPcode() {
      return DPcode;
    }

    public void setDPcode(String DPcode) {
      this.DPcode = DPcode;
    }

    public String getDistrict() {
      return District;
    }

    public void setDistrict(String district) {
      District = district;
    }

    public String getTSPcode() {
      return TSPcode;
    }

    public void setTSPcode(String TSPcode) {
      this.TSPcode = TSPcode;
    }

    public String getTownshipName() {
      return TownshipName;
    }

    public void setTownshipName(String townshipName) {
      TownshipName = townshipName;
    }

    public String getTownPcode() {
      return TownPcode;
    }

    public void setTownPcode(String townPcode) {
      TownPcode = townPcode;
    }

    public String getTownName() {
      return TownName;
    }

    public void setTownName(String townName) {
      TownName = townName;
    }

    public String getTownNameBurmese() {
      return TownNameBurmese;
    }

    public void setTownNameBurmese(String townNameBurmese) {
      TownNameBurmese = townNameBurmese;
    }

    public long getLat() {
      return lat;
    }

    public void setLat(long lat) {
      this.lat = lat;
    }

    public long getLng() {
      return lng;
    }

    public void setLng(long lng) {
      this.lng = lng;
    }
  }
}
