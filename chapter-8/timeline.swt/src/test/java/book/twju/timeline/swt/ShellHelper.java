package book.twju.timeline.swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

class ShellHelper {
  
  static void showShell( Shell shell ) {
    shell.setLayout( new FillLayout() );
    shell.setBounds( 100, 100, 350, 700 );
    shell.open();
  }
}