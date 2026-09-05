import parse from 'html-react-parser';

interface InformativeQuestionProps {
  greeting: string;
  normalInformation: string;
  boldInformation: string;
  questionCode?: string;
}

const InformativeQuestion = ({ greeting, normalInformation, boldInformation, questionCode }: InformativeQuestionProps) => {
  return (
    <>
      <p>{parse(normalInformation)}</p>
      <p>
        {questionCode === "P00214" ? (
          <i>{parse(boldInformation)}</i>
        ) : (
          <strong><i>{parse(boldInformation)}</i></strong>
        )}
      </p>
    </>
  );
};

export default InformativeQuestion;