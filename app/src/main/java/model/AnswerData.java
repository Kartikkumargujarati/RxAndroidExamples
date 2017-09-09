package model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by kartik.gujarati on 6/9/17.
 */

public class AnswerData {
	@SerializedName("items")
	@Expose
	private List<AnswerItem> items = null;
	@SerializedName("has_more")
	@Expose
	private Boolean hasMore;
	@SerializedName("backoff")
	@Expose
	private Integer backoff;
	@SerializedName("quota_max")
	@Expose
	private Integer quotaMax;
	@SerializedName("quota_remaining")
	@Expose
	private Integer quotaRemaining;

	public List<AnswerItem> getItems () {
		return items;
	}

	public void setItems(List<AnswerItem> items) {
		this.items = items;
	}

	public Boolean getHasMore() {
		return hasMore;
	}

	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}

	public Integer getBackoff() {
		return backoff;
	}

	public void setBackoff(Integer backoff) {
		this.backoff = backoff;
	}

	public Integer getQuotaMax() {
		return quotaMax;
	}

	public void setQuotaMax(Integer quotaMax) {
		this.quotaMax = quotaMax;
	}

	public Integer getQuotaRemaining() {
		return quotaRemaining;
	}

	public void setQuotaRemaining(Integer quotaRemaining) {
		this.quotaRemaining = quotaRemaining;
	}
}
