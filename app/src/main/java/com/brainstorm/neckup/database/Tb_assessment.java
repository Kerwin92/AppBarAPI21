package com.brainstorm.neckup.database;

public class Tb_assessment {
	/**
	 * �����?�����У�����id,��������
	 */
	private static final String TAG = "Tb_assessment";
	private int _id; //
	private String aexplain;

	/* Ĭ�Ϲ��캯�� */
	public Tb_assessment() {
		super();
	}

	/* ���� ID */
	/* ������������ID�Ŀɶ����� */
	public final int getid() {
		return _id;
	}

	/* ������������ID�Ŀ�д���� */
	public final void setid(int id) {
		this._id = id;
	}

	/* �������� */
	// ��������������ϸ���͵Ŀɶ�����
	public final String getAexplain() {
		return aexplain;
	}

	// ��������������ϸ���͵Ŀ�д����
	public final void setAexplain(String aexplain) {
		this.aexplain = aexplain;
	}
}
