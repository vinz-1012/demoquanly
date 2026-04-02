package com.tutorialspoint.jdbc.com.quanlygara.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

/**
 * Utility class for creating rounded borders and modern UI effects
 */
public class UIUtils {
    
    /**
     * Creates a rounded border with specified corner radius and color
     */
    public static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;
        private final int thickness;
        
        public RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new java.awt.BasicStroke(thickness));
            
            Shape shape = new RoundRectangle2D.Float(
                x + thickness/2, 
                y + thickness/2, 
                width - thickness, 
                height - thickness, 
                radius, 
                radius
            );
            g2d.draw(shape);
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness + 2, thickness + 2, thickness + 2, thickness + 2);
        }
    }
    
    /**
     * Creates a filled rounded panel with gradient background
     */
    public static class RoundedPanel extends javax.swing.JPanel {
        private final int radius;
        private final Color backgroundColor;
        private final Color gradientColor;
        private final boolean useGradient;
        
        public RoundedPanel(int radius, Color backgroundColor) {
            this(radius, backgroundColor, null, false);
        }
        
        public RoundedPanel(int radius, Color backgroundColor, Color gradientColor, boolean useGradient) {
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            this.gradientColor = gradientColor;
            this.useGradient = useGradient;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (useGradient && gradientColor != null) {
                // Create gradient
                java.awt.GradientPaint gradient = new java.awt.GradientPaint(
                    0, 0, backgroundColor, 
                    getWidth(), getHeight(), gradientColor
                );
                g2d.setPaint(gradient);
            } else {
                g2d.setColor(backgroundColor);
            }
            
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2d.dispose();
        }
    }
    
    /**
     * Creates a modern button with rounded corners and hover effects
     */
    public static class ModernButton extends javax.swing.JButton {
        private final int radius;
        private final Color normalColor;
        private final Color hoverColor;
        private final Color pressedColor;
        
        public ModernButton(String text, int radius, Color normalColor, Color hoverColor, Color pressedColor) {
            super(text);
            this.radius = radius;
            this.normalColor = normalColor;
            this.hoverColor = hoverColor;
            this.pressedColor = pressedColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
            setBackground(normalColor); // Set initial background
            
            // Add hover effects
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setBackground(hoverColor);
                    repaint();
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setBackground(normalColor);
                    repaint();
                }
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    setBackground(pressedColor);
                    repaint();
                }
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    if (getBounds().contains(evt.getPoint())) {
                        setBackground(hoverColor);
                    } else {
                        setBackground(normalColor);
                    }
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color bgColor = getBackground();
            if (bgColor == null) bgColor = normalColor;
            
            // Draw shadow
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, radius, radius);
            
            // Draw button
            g2d.setColor(bgColor);
            g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, radius, radius);
            
            // Draw text
            g2d.setColor(getForeground());
            g2d.setFont(getFont());
            java.awt.FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(getText(), x, y);
            
            g2d.dispose();
        }
    }
    
    /**
     * Creates a modern text field with rounded corners
     */
    public static class ModernTextField extends javax.swing.JTextField {
        private final int radius;
        private final Color borderColor;
        private final Color focusColor;
        
        public ModernTextField(int radius, Color borderColor, Color focusColor) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.focusColor = focusColor;
            setOpaque(false);
            setBorder(new javax.swing.border.EmptyBorder(8, 12, 8, 12));
            setBackground(Color.WHITE);
            
            addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    repaint();
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw background
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Draw border
            Color currentBorderColor = isFocusOwner() ? focusColor : borderColor;
            g2d.setColor(currentBorderColor);
            g2d.setStroke(new java.awt.BasicStroke(1.5f));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            
            g2d.dispose();
            super.paintComponent(g);
        }
    }
}
