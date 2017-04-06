/**
 * 
 */
package com.craigslist.websocket;

/**
 * @author amaheedhara
 *
 */
public class Task {

	  private String id;
	  private Client owner;
	  private String title;
	  private String description;
	  private long duration;
	  private boolean universal;
	  
	  public Task() {
	    this.id = this.toString();
	  }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public boolean isUniversal() {
		return universal;
	}

	public void setUniversal(boolean universal) {
		this.universal = universal;
	}
	  
	  
}
