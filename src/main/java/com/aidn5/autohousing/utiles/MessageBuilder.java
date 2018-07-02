package com.aidn5.autohousing.utiles;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * Class for building (and sending) new chat messages
 */
public class MessageBuilder {
	/**
	 * Basic function that builds a red chat message
	 * 
	 * @param error
	 *            Message to be sent
	 * @return IChatComponent of the message
	 */
	public static IChatComponent buildError(String error) {
		return build(error, EnumChatFormatting.RED);
	}

	/**
	 * Basic function that builds a green chat message
	 * 
	 * @param msg
	 *            Message to be sent
	 * @return IChatComponent of the message
	 */
	public static IChatComponent buildSuccess(String msg) {
		return build(msg, EnumChatFormatting.GREEN);
	}

	/**
	 * Basic simple message constructor
	 * 
	 * @param msg
	 *            Message to be sent
	 * @param code
	 *            Color code for the message.
	 * @return IChatComponent of the message
	 */
	public static IChatComponent build(String msg, EnumChatFormatting code) {
		IChatComponent component = new ChatComponentText(msg);
		ChatStyle style = new ChatStyle();

		style.setColor(code);
		component.setChatStyle(style);

		return component;
	}

	/**
	 * Send a chat message to the player
	 * 
	 * @param component
	 *            IChatComponent to be sent
	 */
	public static void send(IChatComponent component) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(component);
	}
}
