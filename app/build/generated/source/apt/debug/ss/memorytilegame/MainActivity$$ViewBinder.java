// Generated code from Butter Knife. Do not modify!
package ss.memorytilegame;

import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MainActivity$$ViewBinder<T extends MainActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends MainActivity> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.rulesButton = finder.findRequiredViewAsType(source, 2131558553, "field 'rulesButton'", Button.class);
      target.rulesView = finder.findRequiredViewAsType(source, 2131558552, "field 'rulesView'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.rulesButton = null;
      target.rulesView = null;

      this.target = null;
    }
  }
}
