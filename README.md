# MultiPanelVolumeRenderer

This is a test java application that demonstrates how to render a volume in a vtkRenderWindowPanel.
The application renders 4 different vtkRenderWindowPanels.

## Build

Before compiling this application, compile VTK with the CMake option *VTK_WRAP_JAVA* enabled.

To compile this java application,

- VTK < v9.0

  ```
  $ cd JavaMultiPanelVolumeRenderer
  $ javac -d . -classpath ".:<VTK_BLD>/lib/vtk.jar:" JavaMultiPanelVolumeRenderer.java
  ```

- VTK >= 9.0

  ```
  $ cd JavaMultiPanelVolumeRenderer
  $ javac -d . -classpath ".:<VTK_BLD>/lib/java/vtk.jar:" JavaMultiPanelVolumeRenderer.java
  ```

## Run

To run the application,

- VTK < 9.0

  ```
  $ LD_LIBRARY_PATH=<VTK_BLD>/lib/:$LD_LIBRARY_PATH java -classpath ".:<VTK_BLD>/lib/vtk.jar:" JavaMultiPanelVolumeRenderer
  ```

- VTK >= 9.0

  ```
  $ LD_LIBRARY_PATH=<VTK_BLD>/lib/java/vtk-Linux-x86_64/:$LD_LIBRARY_PATH java -classpath ".:<VTK_BLD>/lib/java/vtk.jar:" JavaMultiPanelVolumeRenderer
  ```
