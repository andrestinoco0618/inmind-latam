
interface InformativeQuestionProps {
  greeting: string;
  normalInformation: string;
  boldInformation: string;
}

const InformativeQuestion = ({ greeting, normalInformation, boldInformation }: InformativeQuestionProps) => {
  return (
    <>
    <p><strong>{greeting}</strong>&nbsp;{normalInformation}
    </p>
    <p><strong><i>{boldInformation}</i></strong>
    </p>
    </>
  );
};

export default InformativeQuestion;