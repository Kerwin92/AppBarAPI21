package com.brainstorm.neckup.database;

public class Tb_question {
	/**

	 */
	private static final String TAG = "Tb_question";
	private int _id; //
	private String qname;
	private String qexplain;

	public Tb_question() {
		super();
	}

	public final int getid() {
		return _id;
	}

	public final void setid(int id) {
		this._id = id;
	}

	public final String getQname() {
		return qname;
	}

	public final void setQname(String qname) {
		this.qname = qname;
	}

	public final String getQexplain() {
		return qexplain;
	}

	public final void setQexplain(String qexplain) {
		this.qexplain = qexplain;
	}
}
