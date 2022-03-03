
export interface SwapReq{

  id : number;
  chatid : number;
  userComment: string;
  lesson: string;
  lessonDate: Date;
  lessonTime :string;
  sch : string;
  classType : string;
  lessonType : string;

}

export interface TelegramMsg{
  chatid : number;
  contact : string;
  msg: string;
}
