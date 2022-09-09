package com.oceancloud.grampus.framework.core.utils;

/**
 * Pool of <code>Char</code> constants to prevent repeating of
 * 
 * @author Beck
 */
public interface CharPool {
	// @formatter:off
	char AMPERSAND        	= '&';
	char AT               = '@';
	char ASTERISK         = '*';
	char STAR             = ASTERISK;
	char BACK_SLASH       = '\\';
	char COLON            = ':';
	char COMMA            = ',';
	char DASH             = '-';
	char DOLLAR           = '$';
	char DOT              = '.';

	char EQUALS           = '=';
	char SLASH            = '/';
	char HASH             = '#';
	char HAT              = '^';
	char LEFT_BRACE       = '{';
	char LEFT_BRACKET     = '(';
	char LEFT_CHEV        = '<';
	char NEWLINE          = '\n';
	char N                = 'n';
	char PERCENT          = '%';
	char PIPE             = '|';
	char PLUS             = '+';
	char QUESTION_MARK    = '?';
	char EXCLAMATION_MARK = '!';
	char QUOTE            = '\'';
	char RETURN           = '\r';
	char TAB              = '\t';
	char RIGHT_BRACE      = '}';
	char RIGHT_BRACKET    = ')';
	char RIGHT_CHEV       = '>';
	char SEMICOLON        = ';';
	char SINGLE_QUOTE     = '\'';
	char BACKTICK         = '`';
	char SPACE            = ' ';
	char TILDA            = '~';
	char LEFT_SQ_BRACKET  = '[';
	char RIGHT_SQ_BRACKET = ']';
	char UNDERSCORE       = '_';
	char Y                = 'y';
	char ONE 				= '1';
	char ZERO				= '0';

	// ---------------------------------------------------------------- array

	char[] EMPTY_ARRAY = new char[0];
	// @formatter:on
}
