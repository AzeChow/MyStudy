package test;

import javax.swing.JOptionPane;

import org.junit.Test;

public class TestAll {

	@Test
	public void testUit() throws Exception {

		JOptionPane.showOptionDialog(null, "数据中有错误信息，是否继续保存数据？", "提示",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				new Object[] { "是", "否" }, "否");
	}
}
