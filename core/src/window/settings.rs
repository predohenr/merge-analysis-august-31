//! Configure your windows.
#[cfg(target_os = "windows")]
#[path = "settings/windows.rs"]
mod platform;

#[cfg(target_os = "macos")]
#[path = "settings/macos.rs"]
mod platform;

#[cfg(target_os = "linux")]
#[path = "settings/linux.rs"]
mod platform;

#[cfg(target_arch = "wasm32")]
#[path = "settings/wasm.rs"]
mod platform;

#[cfg(not(any(
    target_os = "windows",
    target_os = "macos",
    target_os = "linux",
    target_arch = "wasm32"
)))]
#[path = "settings/other.rs"]
mod platform;

use crate::window::{Icon, Level, Position};
use crate::Size;

pub use platform::PlatformSpecific;

/// The window settings of an application.
#[derive(Debug, Clone)]
pub struct Settings {
    /// The initial logical dimensions of the window.
    pub size: Size,
    pub maximized: bool,
    pub fullscreen: bool,
    pub position: Position,
    pub min_size: Option<Size>,
    pub max_size: Option<Size>,
    pub visible: bool,
    pub resizable: bool,
    pub decorations: bool,
    pub transparent: bool,
    pub level: Level,
    pub icon: Option<Icon>,
    pub platform_specific: PlatformSpecific,
    pub exit_on_close_request: bool,

    /// Whether the window should start maximized.,

    /// Whether the window should start fullscreen.,

    /// The initial position of the window.,

    /// The minimum size of the window.,

    /// The maximum size of the window.,

    /// Whether the window should be visible or not.,

    /// Whether the window should be resizable or not.,

    /// Whether the window should have a border, a title bar, etc. or not.,

    /// Whether the window should be transparent.,

    /// The window [`Level`].,

    /// The icon of the window.,

    /// Platform specific settings.,

    /// Whether the window will close when the user requests it, e.g. when a user presses the
    /// close button.
    ///
    /// This can be useful if you want to have some behavior that executes before the window is
    /// actually destroyed. If you disable this, you must manually close the window with the
    /// `window::close` command.
    ///
    /// By default this is enabled.,
}

impl Default for Settings {
    fn default() -> Self {
        Self {
            size: Size::new(1024.0, 768.0),
            maximized: false,
            fullscreen: false,
            position: Position::default(),
            min_size: None,
            max_size: None,
            visible: true,
            resizable: true,
            decorations: true,
            transparent: false,
            level: Level::default(),
            icon: None,
            exit_on_close_request: true,
            platform_specific: PlatformSpecific::default(),
        }
    }
}
