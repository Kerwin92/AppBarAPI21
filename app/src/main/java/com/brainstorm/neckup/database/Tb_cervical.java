package com.brainstorm.neckup.database;

import java.io.Serializable;

public class Tb_cervical implements Serializable {

	/**
	 * ��׵��?��Ա�����У���׵�����֣���׵��������ԭ�?ע�������ͼ��video·��
	 */
	private static final long serialVersionUID = 1L;
	private static final String TAG = "Tb_cervical";
	private int _id; //
	private String cervi_name;
	private String cervi_zuofa;
	private String cervi_zhuyi;
	private String cervi_yuanli;
	private String cervi_img;
	private String cervi_video;

	/* Ĭ�Ϲ��캯�� */
	public Tb_cervical() {
		super();
	}

	/* ��׵�� ID */
	// ���þ�׵��ID�Ŀɶ�����
	public final int getid() {
		return _id;
	}

	// ���þ�׵��ID�Ŀ�д����
	public final void setid(int id) {
		this._id = id;
	}

	/* ��׵���� */
	// 获取颈椎操名
	public final String getCervi_name() {
		return cervi_name;
	}

	// 设置颈椎操名
	public final void setCervi_name(String cervi_name) {
		this.cervi_name = cervi_name;
	}

	/* ��׵������ */
	// ���þ�׵�������Ŀɶ�����
	public final String getCervi_zuofa() {
		return cervi_zuofa;
	}

	// ���þ�׵����ƵĿ�д����
	public final void setCervi_zuofa(String cervi_zuofa) {
		this.cervi_zuofa = cervi_zuofa;
	}

	/* ��׵��ע������ */
	// ���þ�׵��ע������Ŀɶ�����
	public final String getCervi_zhuyi() {
		return cervi_zhuyi;

	}

	// ���þ�׵��ע������Ŀ�д����
	public final void setCervi_zhuyi(String cervi_zhuyi) {
		this.cervi_zhuyi = cervi_zhuyi;
		// Log.i(TAG, "set nickname: " + this.cervi_zhuyi);
	}

	/* ��׵��ԭ�� */
	// ���þ�׵��ԭ��Ŀɶ�����
	public final String getCervi_yuanli() {
		return cervi_yuanli;
	}

	// ���þ�׵��ԭ��Ŀ�д����
	public final void setCervi_yuanli(String cervi_yuanli) {
		this.cervi_yuanli = cervi_yuanli;
		// Log.i(TAG, "set sex: " + this.cervi_yuanli);
	}

	/* ��׵�ٽ�ͼ */
	// ��ȡ��׵��Ԥ����ͼ·���Ŀɶ�����
	public final String getCervi_img() {
		return cervi_img;
	}

	// ���þ�׵��Ԥ����ͼ·���Ŀ�д����
	public final void setCervi_img(String cervi_img) {
		this.cervi_img = cervi_img;
		// Log.i(TAG, this._id+"");
	}

	/* ��׵����Ƶ·�� */
	// ���þ�׵����Ƶ·���Ŀɶ�����
	public final String getCervi_video() {
		return cervi_video;
	}

	// ���þ�׵����Ƶ·���Ŀ�д����
	public final void setCervi_video(String cervi_video) {
		this.cervi_video = cervi_video;
		// Log.i(TAG, this.u_id);
	}

}
