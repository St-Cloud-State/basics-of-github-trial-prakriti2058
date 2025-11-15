# Warehouse Management System - GUI Version

This is a graphical user interface (GUI) implementation of the Warehouse Management System using Java Swing, built on top of a Finite State Machine (FSM) architecture.

## Project Structure

The project implements the following states:
- **LoginState**: Main entry point for user authentication
- **ManagerMenuState**: Manager operations (add products, manage inventory, view waitlists)
- **ClerkMenuState**: Clerk operations (manage clients, record payments, view reports)
- **ClientMenuState**: Client operations (view account, browse products, access wishlist)
- **WishlistOperationsState**: Dedicated wishlist management (NEW STATE ADDED)

## New Features

### Added WishlistOperationsState
As required, we've moved wishlist operations from ClientMenuState to a dedicated state:
- Add items to wishlist
- Display wishlist contents
- Remove items from wishlist
- Place orders for all wishlist items
- Navigate back to client menu

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt access

### Compilation
```bash
cd WarehouseFSMGUI
javac *.java
```

### Running the Application
```bash
java WarehouseGUI
```

## GUI Features

### Professional Business Interface
- Clean, corporate design suitable for logistics businesses
- Light background with professional color scheme for optimal readability
- Role-specific color coding (Manager: Indigo, Clerk: Blue, Client: Green, Wishlist: Purple)
- Clean, icon-free interface focusing on functionality
- Consistent typography using Arial font for business compatibility
- High contrast text for excellent visibility and accessibility
- Professional button styling with subtle borders and hover effects
- Material Design color palette for modern, professional appearance
- Structured headers with clear role identification and functionality descriptions

### State Management
- Seamless transitions between different user roles
- Proper state history management
- Context preservation during state changes
- Error handling with user-friendly messages

## Testing Sequences

The application supports all required testing sequences:

1. **Manager Operations**: Create products P1-P5 with specified quantities and prices
2. **Clerk Operations**: Create clients C1-C5, view client information, record payments
3. **Client Operations**: Login as different clients, manage wishlists, place orders
4. **Wishlist Management**: Add/remove items, view wishlist contents, place orders

## Architecture

### FSM Implementation
- State pattern with clear separation of concerns
- Event-driven transitions between states
- Centralized state management through WarehouseGUI
- Proper cleanup and context management

### GUI Components
- Each state has its own GUI panel
- CardLayout for seamless panel switching
- Consistent styling across all components
- Responsive design with proper sizing

## Files Included

- `WarehouseGUI.java` - Main GUI controller and state manager
- `LoginStateGUI.java` - Login interface
- `ManagerMenuStateGUI.java` - Manager operations interface
- `ClerkMenuStateGUI.java` - Clerk operations interface  
- `ClientMenuStateGUI.java` - Client operations interface
- `WishlistOperationsState.java` - Wishlist management interface
- `StateId.java` - State enumeration (updated with WISHLIST state)
- `Event.java` - Event enumeration (updated with TO_WISHLIST event)
- Supporting classes: `State.java`, `WarehouseContext.java`, `Client.java`, `Product.java`, etc.

## Author

Prakriti Sathapa
GitHub: prakriti2058

## Completion Report

**Added State**: WishlistOperationsState - Moved wishlist operations from ClientMenuState to provide dedicated wishlist management functionality.

**GitHub Repository**: basics-of-github-trial-prakriti2058

**How to Run**: 
1. Navigate to WarehouseFSMGUI directory
2. Compile: `javac *.java`
3. Run: `java WarehouseGUI`

**GUI Features Implemented**: All states converted to GUI with full functionality, intuitive design, and proper error handling.