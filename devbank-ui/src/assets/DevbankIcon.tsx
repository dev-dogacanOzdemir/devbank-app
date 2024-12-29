import appIcon from './devbankLogoTitleWhite.png';

export function DevbankIcon() {
    return (
        <div style={{display: 'flex', alignItems: 'center'}}>
            <img  src={appIcon} alt="App Icon"  width={280} height={150}/>
        </div>
    );
}