package uak.launcher;
import android.appwidget.*;

public interface WidgetListener {
	public void widgetSelected(int appWidgetId, WidgetManager widgetManager);
	public void widgetConfigured(int appWidgetId, WidgetManager widgetManager);
	public void widgetCreated(int appWidgetId, AppWidgetHostView view, WidgetManager widgetManager);
}
