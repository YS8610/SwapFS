from flask import Flask, request, jsonify #, make_response
from credentials import bot_token
import requests
import telegram
from telegram import InlineKeyboardButton, InlineKeyboardMarkup
from telegram.ext import Dispatcher, CommandHandler, CallbackQueryHandler
import logging
import os


logger = logging.getLogger('Whook')
logger.setLevel(logging.DEBUG)
# create file handler which logs even debug messages
fh = logging.FileHandler('/var/log/Whook.log')
fh.setLevel(logging.DEBUG)
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
fh.setFormatter(formatter)
logger.addHandler(fh)


app = Flask(__name__)
application = app # our hosting requires application in passenger_wsgi
URL_API = "http://188.166.180.144"


def start(update, context):
    context.bot.send_chat_action(chat_id=update.effective_chat.id, action='TYPING', timeout=None)
    if update.effective_message.reply_to_message:
        context.bot.send_message(chat_id=update.effective_chat.id ,reply_to_message_id=update.effective_message.reply_to_message.message_id, text='Hi! I am CDC Bot!')
    else:
        context.bot.send_message(chat_id=update.effective_chat.id ,reply_to_message_id=update.effective_message.message_id, text='Hi! I am CDC Bot!')
#   bot.send_message(chat_id=update.message.chat_id, text="I'm a bot, please talk to me")


def chatid(update, context):
#   logger.info('chatid function called')
    if update.effective_chat.id > 1:
        context.bot.send_chat_action(chat_id=update.effective_chat.id, action='TYPING', timeout=None)
        context.bot.send_message(chat_id=update.effective_chat.id ,reply_to_message_id=update.effective_message.message_id, text='Chat id is {}'.format(update.message.chat_id))
    else:
        context.bot.delete_message(chat_id=update.effective_chat.id,message_id=update.effective_message.message_id)
#    logger.info('chatid funtion used')
    #bot.send_message(chat_id=update.message.chat_id, text="I'm a bot, please talk to me")


def getSwap(update, context):
    URL = URL_API+"/chatid/"
    if update.effective_chat.id > 1:
        context.bot.send_chat_action(chat_id=update.effective_chat.id, action='TYPING', timeout=None)
        resp = requests.get(URL+str(update.message.chat_id))
        if resp.status_code==200:
            swapsChatid = resp.json()
            replyString = ""
            for i,j in enumerate(swapsChatid,1):
                replyString += f"{i}. Class {j['classType']} {j['lesson']}@{j['lessonDate']} {j['lessonTime']}\n\n"
            # replyString += os.environ["testVariable"]
            context.bot.send_message(chat_id=update.effective_chat.id ,reply_to_message_id=update.effective_message.message_id, text=replyString)
        else:
            context.bot.send_message(chat_id=update.effective_chat.id ,reply_to_message_id=update.effective_message.message_id, text=f'No Swap found. Please submit Swap request <a href="{URL_API}">here</a>',parse_mode=telegram.ParseMode.HTML)
    else:
        context.bot.delete_message(chat_id=update.effective_chat.id,message_id=update.effective_message.message_id)


def deleteSwap(update,context):
    URL = URL_API+"/api/chatid/"
    if update.effective_chat.id > 1:
        context.bot.send_chat_action(chat_id=update.effective_chat.id, action='TYPING', timeout=None)
        resp = requests.get(URL+str(update.message.chat_id))
        if resp.status_code>200:
            context.bot.send_message(chat_id=update.effective_chat.id ,reply_to_message_id=update.effective_message.message_id, text=f'No Swap found. Please submit Swap request <a href="{URL_API}">here</a>',parse_mode=telegram.ParseMode.HTML)
        else:
            swapsChatid = resp.json()
            keyboard =[]
            for i,j in enumerate(swapsChatid,1):
                keyboard.append( [ InlineKeyboardButton(f"Class {j['classType']} {j['lesson']}@{j['lessonDate']} {j['lessonTime']}", callback_data=f"swap,{j['id']},{j['lesson']}@{j['lessonDate']} {j['lessonTime']} ") ] )
            keyboard.append( [ InlineKeyboardButton("Cancel Deletion", callback_data=f"swap,delete,Deletion of Swap request cancelled") ] )
            reply_markup = InlineKeyboardMarkup(keyboard)
            context.bot.send_message(chat_id=update.effective_chat.id, text='Please choose to delete:', reply_markup=reply_markup ,parse_mode=telegram.ParseMode.HTML )


def button(update, context):
    URL = URL_API+"/api/delete/"
    query = update.callback_query
    if query.data[:4] == 'swap':
        if query.data.split(",")[1] == "delete":
            query.edit_message_text(text=f'{query.data.split(",")[2]}')
        else:
            payload = {"id":query.data.split(",")[1]}
            headers = {"authorization": os.environ["JWT_TFIP"]}
            resp = requests.post(URL, data=payload,headers=headers)
            if resp.status_code==200:
                query.edit_message_text(text=f'{query.data.split(",")[2]} Request deleted')
            else:
                query.edit_message_text(text=f'{query.data.split(",")[2]} Deletion failed')


def error(update, context):
    logger.warning('Update "%s" caused error "%s"', update, context.error)

def setup(bot):
    # Create bot, update queue and dispatcher instances
#    bot = telegram.Bot(token)
    dispatcher = Dispatcher(bot, None, workers=0, use_context=True)
    ##### Register handlers here #####
    dispatcher.add_handler(CommandHandler('start', start))
    dispatcher.add_handler(CommandHandler('chatid', chatid))
    dispatcher.add_handler(CommandHandler('swap', getSwap))#
    dispatcher.add_handler(CommandHandler('delete', deleteSwap))#
    dispatcher.add_handler(CallbackQueryHandler(button))
    dispatcher.add_error_handler(error)
    return dispatcher

TOKEN = bot_token
bot = telegram.Bot(token=TOKEN)
dispatcher=setup(bot)

@app.route("/{}".format(TOKEN), methods=["POST","GET"])
def respond():
    if request.method == "GET":
        return "Working Webhook"
    else:
        try:
            update = telegram.Update.de_json(request.get_json(force=True), bot) # retrieve the message in JSON and then transform it to Telegram object
            dispatcher.process_update(update)
            response = jsonify(message="ok")
            return (response,200)
        except Exception as e:
            return "Wrong Request or Error"


@app.route("/")
def hello():
    return "This is Hello World!\n"


@app.route("/camper", methods=["GET"])
def camperBot():
    if request.method == "GET":
        args = request.args
        chatid = args.get("chatid")
        msg = args.get("msg")
        if None not in (chatid,msg) and chatid !=0:
            bot.send_message(chat_id=int(chatid), text=msg,parse_mode=telegram.ParseMode.HTML)
            response = jsonify(message="Sent Successfully")
            response.headers.add("Access-Control-Allow-Origin", URL_API)
            return response
        response = jsonify(message="error")
        return (response,400)
    else:
        response = jsonify(message="error")
        return (response,400)


if __name__ == "__main__":
    app.run()