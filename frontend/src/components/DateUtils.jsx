// yymmdd 형식으로 포맷
export const formatYYMMDD = (date) => {
  return `${date.getFullYear().toString().slice(2)}${(date.getMonth() + 1)
    .toString()
    .padStart(2, "0")}${date.getDate().toString().padStart(2, "0")}`;
};

// yyyy.mm.dd 형식으로 포맷
export const formatYYYYMMDD = (date) => {
  return `${date.getFullYear()}.${(date.getMonth() + 1)
    .toString()
    .padStart(2, "0")}.${date.getDate().toString().padStart(2, "0")}`;
};

// 오늘 날짜 yymmdd 형식
export const getFormattedTodayYYMMDD = () => {
  const now = new Date();
  return formatYYMMDD(now);
};

// 내일 날짜 yymmdd 형식
export const getFormattedTomorrowYYMMDD = () => {
  const tomorrow = new Date();
  tomorrow.setDate(tomorrow.getDate() + 1);
  return formatYYMMDD(tomorrow);
};

// 오늘 날짜 yyyy.mm.dd 형식
export const getFormattedTodayYYYYMMDD = () => {
  const now = new Date();
  return formatYYYYMMDD(now);
};

// 내일 날짜 yyyy.mm.dd 형식
export const getFormattedTomorrowYYYYMMDD = () => {
  const tomorrow = new Date();
  tomorrow.setDate(tomorrow.getDate() + 1);
  return formatYYYYMMDD(tomorrow);
};
// yymm 형식으로 포맷
export const formatYYMM = (date) => {
  return `${date.getFullYear().toString().slice(2)}${(date.getMonth() + 1)
    .toString()
    .padStart(2, "0")}`;
};

// 오늘 날짜 yymm 형식
export const getFormattedTodayYYMM = () => {
  const now = new Date();
  return formatYYMM(now);
};
