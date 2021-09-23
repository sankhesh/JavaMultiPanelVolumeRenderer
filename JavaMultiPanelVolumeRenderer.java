import java.awt.*;
import javax.swing.*;

import vtk.*;

public class JavaMultiPanelVolumeRenderer extends JFrame {

  static {
    if (!vtkNativeLibrary.LoadAllNativeLibraries()) {
      for (vtkNativeLibrary lib : vtkNativeLibrary.values()) {
        if (!lib.IsLoaded()) {
          System.out.println(lib.GetLibraryName() + " not loaded");
        }
      }
    }
    vtkNativeLibrary.DisableOutputWindow(null);
  }

  private vtkRenderWindowPanel renderWindowPanel1;
  private vtkRenderWindowPanel renderWindowPanel2;
  private vtkRenderWindowPanel renderWindowPanel3;
  private vtkRenderWindowPanel renderWindowPanel4;

  private vtkVolumeProperty property;

  public JavaMultiPanelVolumeRenderer() {
    vtkNamedColors colors = new vtkNamedColors();

    double bkg1[] = new double[4];
    double bkg2[] = new double[4];
    double bkg3[] = new double[4];
    double bkg4[] = new double[4];
    colors.GetColor("RoyalBlue", bkg1);
    colors.GetColor("LemonChiffon", bkg2);
    colors.GetColor("Orchid", bkg3);
    colors.GetColor("LightSkyBlue", bkg4);

    // Create the wavelet
    vtkRTAnalyticSource wavelet = new vtkRTAnalyticSource();
    wavelet.SetWholeExtent(-100, 100, -100, 100, -100, 100);
    property = new vtkVolumeProperty();
    property.SetShade(1);

    vtkColorTransferFunction ctf = new vtkColorTransferFunction();
    ctf.AddRGBPoint(37, 0.2, 0.3, 1);
    ctf.AddRGBPoint(157, 0.3, 0.1, 0.6);
    ctf.AddRGBPoint(280, 1, 0.01, 0.1);

    vtkPiecewiseFunction pf = new vtkPiecewiseFunction();
    pf.AddPoint(37, 0.0);
    pf.AddPoint(280, 0.01);

    property.SetColor(ctf);
    property.SetScalarOpacity(pf);

    vtkGPUVolumeRayCastMapper mapper = new vtkGPUVolumeRayCastMapper();
    mapper.SetInputConnection(wavelet.GetOutputPort());

    vtkVolume volume = new vtkVolume();
    volume.SetMapper(mapper);
    volume.SetProperty(property);

    // Create a render window panel to display the volume
    renderWindowPanel1 = new vtkRenderWindowPanel();
    renderWindowPanel1.setPreferredSize(new Dimension(300, 600));
    renderWindowPanel1.setInteractorStyle(new vtkInteractorStyleTrackballCamera());

    // Create a render window panel to display the volume
    renderWindowPanel2 = new vtkRenderWindowPanel();
    renderWindowPanel2.setPreferredSize(new Dimension(300, 600));
    renderWindowPanel2.setInteractorStyle(new vtkInteractorStyleTrackballCamera());

    // Create a render window panel to display the volume
    renderWindowPanel3 = new vtkRenderWindowPanel();
    renderWindowPanel3.setPreferredSize(new Dimension(300, 600));
    renderWindowPanel3.setInteractorStyle(new vtkInteractorStyleTrackballCamera());

    // Create a render window panel to display the volume
    renderWindowPanel4 = new vtkRenderWindowPanel();
    renderWindowPanel4.setPreferredSize(new Dimension(300, 600));
    renderWindowPanel4.setInteractorStyle(new vtkInteractorStyleTrackballCamera());

    add(renderWindowPanel1, BorderLayout.NORTH);
    add(renderWindowPanel2, BorderLayout.SOUTH);
    add(renderWindowPanel3, BorderLayout.WEST);
    add(renderWindowPanel4, BorderLayout.EAST);

    renderWindowPanel1.GetRenderer().AddVolume(volume);
    renderWindowPanel2.GetRenderer().AddVolume(volume);
    renderWindowPanel3.GetRenderer().AddVolume(volume);
    renderWindowPanel4.GetRenderer().AddVolume(volume);
    renderWindowPanel1.GetRenderer().SetBackground(bkg1);
    renderWindowPanel2.GetRenderer().SetBackground(bkg2);
    renderWindowPanel3.GetRenderer().SetBackground(bkg3);
    renderWindowPanel4.GetRenderer().SetBackground(bkg4);
  }

  public static void main(String[] args) {
    try {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          JPopupMenu.setDefaultLightWeightPopupEnabled(false);
          ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

          JFrame frame = new JavaMultiPanelVolumeRenderer();
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.pack();
          frame.setPreferredSize(new Dimension(600, 600));
          frame.setVisible(true);
          ((JavaMultiPanelVolumeRenderer) frame).render();
          ((JavaMultiPanelVolumeRenderer) frame).renderTimed();
          ((JavaMultiPanelVolumeRenderer) frame).gc();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void renderTimed() {
    // Change something to re-trigger the whole pipeline
    property.SetShade(0);
    long startTime = System.nanoTime();
    render();
    long stopTime = System.nanoTime();
    System.out.format("Time to render: %f msec", (stopTime - startTime) / 10000000.0);
  }

  public void render() {
    renderWindowPanel1.Render();
    renderWindowPanel2.Render();
    renderWindowPanel3.Render();
    renderWindowPanel4.Render();
  }

  public void gc() {
    property.Delete();
  }

}
